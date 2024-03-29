package com.one.domain.user;

import com.one.domain.category.infrastructure.CategoryMapper;
import com.one.domain.file.infrastructure.ImageFileMapper;
import com.one.domain.file.domain.ImageFile;
import com.one.domain.sms.exception.NotAuthenticatedPhoneNumberException;
import com.one.domain.user.domain.*;
import com.one.domain.user.dto.GuestUserSignUpDto;
import com.one.domain.user.dto.HostUserSignUpDto;
import com.one.domain.user.dto.UserSaveDto;
import com.one.domain.user.exception.DuplicateUserIdException;
import com.one.domain.user.exception.PasswordMismatchException;
import com.one.domain.user.infrastructure.UserMapper;
import com.one.domain.user.service.UserSignUpService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("classpath:table-init.sql")
class UserSignUpServiceTest {

    @Autowired
    private UserSignUpService userSignUpService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ImageFileMapper imageFileMapper;

    @Autowired
    private HttpSession httpSession;

    @Test
    @DisplayName("게스트 유저가 회원가입에 성공한다.")
    public void test() {
        final String phoneNumber = "01012345678";
        final GuestUserSignUpDto guestUserSignUpDto = new GuestUserSignUpDto("test", "1234", "1234", "홍길동", phoneNumber);
        httpSession.setAttribute("authenticatedPhoneNumber", phoneNumber);
        userSignUpService.signUp(guestUserSignUpDto);
        final Optional<User> user = userMapper.findByUserId(guestUserSignUpDto.userId());
        assertThat(user.get().id()).isEqualTo(1);
        assertThat(user.get().userId()).isEqualTo(guestUserSignUpDto.userId());
        assertThat(user.get().imageFileId()).isNull();
        assertThat(user.get().password()).isEqualTo(guestUserSignUpDto.password());
        assertThat(user.get().name()).isEqualTo(guestUserSignUpDto.name());
        assertThat(user.get().phoneNumber()).isEqualTo(guestUserSignUpDto.phoneNumber());
        assertThat(user.get().userType()).isEqualTo(UserType.GUEST.getValue());
        assertThat(user.get().userStatus()).isEqualTo(UserStatus.SIGN_UP_SUCCESS.getValue());
    }

    @Test
    @DisplayName("게스트 유저가 휴대폰번호 인증을 완료하지 않아 예외가 발생한다.")
    public void test2() {
        final String phoneNumber = "01012345678";
        final GuestUserSignUpDto guestUserSignUpDto = new GuestUserSignUpDto("test", "1234", "1234", "홍길동", phoneNumber);
        assertThrows(NotAuthenticatedPhoneNumberException.class, () -> userSignUpService.signUp(guestUserSignUpDto));
    }

    @Test
    @DisplayName("게스트 유저가 존재하는 아이디를 입력하여 예외가 발생한다.")
    public void test3() {
        userMapper.save(new UserSaveDto(null, "test", "4567", "테스트", "01011111111", UserType.GUEST.getValue(), UserStatus.SIGN_UP_SUCCESS.getValue()));
        final String phoneNumber = "01012345678";
        httpSession.setAttribute("authenticatedPhoneNumber", phoneNumber);
        final GuestUserSignUpDto guestUserSignUpDto = new GuestUserSignUpDto("test", "1234", "1234", "홍길동", phoneNumber);
        assertThrows(DuplicateUserIdException.class, () -> userSignUpService.signUp(guestUserSignUpDto));
    }

    @Test
    @DisplayName("게스트 유저가 비밀번호와 검증 비밀번호를 다르게 입력하여 예외가 발생한다.")
    public void test4() {
        final String phoneNumber = "01012345678";
        httpSession.setAttribute("authenticatedPhoneNumber", phoneNumber);
        final GuestUserSignUpDto guestUserSignUpDto = new GuestUserSignUpDto("test", "1234", "5678", "홍길동", phoneNumber);
        assertThrows(PasswordMismatchException.class, () -> userSignUpService.signUp(guestUserSignUpDto));
    }

    @Test
    @DisplayName("호스트 유저가 회원가입에 성공한다.")
    public void test21() throws IOException {
        final String phoneNumber = "01012345678";
        httpSession.setAttribute("authenticatedPhoneNumber", phoneNumber);
        final MultipartFile multipartFile = new MockMultipartFile("test", "test.txt", "text/plain", "Hello World".getBytes());
        final HostUserSignUpDto hostUserSignUpDto = new HostUserSignUpDto("test", "1234", "1234", "홍길동", phoneNumber, 1, multipartFile);
        userSignUpService.signUp(hostUserSignUpDto);
        final Optional<User> user = userMapper.findByUserId(hostUserSignUpDto.userId());
        assertThat(user.get().id()).isEqualTo(1);
        assertThat(user.get().userId()).isEqualTo(hostUserSignUpDto.userId());
        assertThat(user.get().password()).isEqualTo(hostUserSignUpDto.password());
        assertThat(user.get().name()).isEqualTo(hostUserSignUpDto.name());
        assertThat(user.get().phoneNumber()).isEqualTo(hostUserSignUpDto.phoneNumber());
        assertThat(user.get().userType()).isEqualTo(UserType.HOST.getValue());
        assertThat(user.get().userStatus()).isEqualTo(UserStatus.SIGN_UP_PROCEEDING.getValue());
        final Integer imageFileId = user.get().imageFileId();
        final Optional<ImageFile> imageFileById = imageFileMapper.findImageFileById(imageFileId);
        assertThat(new FileInputStream(imageFileById.get().path()).readAllBytes()).isEqualTo("Hello World".getBytes(StandardCharsets.UTF_8));
    }
}