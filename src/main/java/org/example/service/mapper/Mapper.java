package org.example.service.mapper;

import org.example.entity.User;
import org.example.service.dto.UserDto;

public interface Mapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
