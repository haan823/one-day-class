package com.one.domain.user.service;

import com.one.domain.category.domain.UserBigCategoryDao;
import com.one.domain.category.dto.UserBigCategorySaveDto;
import com.one.domain.file.domain.ImageFileManager;
import com.one.domain.file.dto.ImageFileSaveDto;
import com.one.domain.sms.domain.SmsAuthenticationManager;
import com.one.domain.user.domain.UserDao;
import com.one.domain.user.dto.UserSaveDto;
import com.one.domain.user.dto.GuestUserSignUpDto;
import com.one.domain.user.dto.HostUserSignUpDto;
import com.one.domain.user.exception.DuplicateUserIdException;
import com.one.domain.user.exception.PasswordMismatchException;
import com.one.domain.file.domain.ImageFileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSignUpService {

    private final UserDao userDao;
    private final ImageFileManager imageFileManager;
    private final SmsAuthenticationManager smsAuthenticationManager;
    private final UserBigCategoryDao userBigCategoryDao;
    private final String fileDir;

    public UserSignUpService(final UserDao userDao, final ImageFileManager imageFileManager, final SmsAuthenticationManager smsAuthenticationManager, final UserBigCategoryDao userBigCategoryDao, @Value("${file.dir}") final String fileDir) {
        this.userDao = userDao;
        this.imageFileManager = imageFileManager;
        this.smsAuthenticationManager = smsAuthenticationManager;
        this.userBigCategoryDao = userBigCategoryDao;
        this.fileDir = fileDir;
    }

    @Transactional
    public void signUp(final GuestUserSignUpDto guestUserSignUpDto) {
        smsAuthenticationManager.checkAuthenticatedPhoneNumber(guestUserSignUpDto.phoneNumber());
        checkDuplicateUserId(guestUserSignUpDto.userId());
        checkPassword(guestUserSignUpDto.password(), guestUserSignUpDto.password2());
        userDao.save(UserSaveDto.from(guestUserSignUpDto));
    }

    @Transactional
    public void signUp(final HostUserSignUpDto hostUserSignUpDto) {
        Optional.ofNullable(hostUserSignUpDto).map(h -> {
                    smsAuthenticationManager.checkAuthenticatedPhoneNumber(h.phoneNumber());
                    return ImageFileSaveDto.of(fileDir, hostUserSignUpDto.multipartFile().getOriginalFilename(), ImageFileType.USER);
                }).map(imageFileSaveDto -> {
                    imageFileManager.save(hostUserSignUpDto.multipartFile(), imageFileSaveDto.path());
                    return imageFileManager.upload(imageFileSaveDto);
                }).map(imageFileId -> userDao.save(UserSaveDto.of(imageFileId, hostUserSignUpDto)).get().id())
                .ifPresent(userId -> userBigCategoryDao.save(new UserBigCategorySaveDto(userId, hostUserSignUpDto.bigCategoryId())));
    }

    public void checkPassword(final String password, final String password2) {
        Optional.ofNullable(password).filter(p -> p.equals(password2)).orElseThrow(PasswordMismatchException::new);
    }

    public void checkDuplicateUserId(final String userId) {
        userDao.findByUserId(userId).orElseThrow(DuplicateUserIdException::new);
    }
}
