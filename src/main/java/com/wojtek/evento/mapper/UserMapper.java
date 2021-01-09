package com.wojtek.evento.mapper;

import com.wojtek.evento.dto.UserDto;
import com.wojtek.evento.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper instance = Mappers.getMapper(UserMapper.class);

    UserDto mapUserToDtoUser(User user);

    @InheritInverseConfiguration
    User mapDtoUserToUser(UserDto userDto);


}
