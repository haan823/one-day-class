package com.one.domain.user.mapper;

import com.one.domain.user.dto.UserSaveRequestDto;
import com.one.domain.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> selectUserById(int id);

    Optional<User> selectUserByUserId(String userId);

    int saveUser(UserSaveRequestDto userSaveRequestDto);
}
