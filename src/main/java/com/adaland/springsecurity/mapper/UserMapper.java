package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto fromUserToUserDto(User source);

    User fromUserDtoToUser(UserDto source);

}