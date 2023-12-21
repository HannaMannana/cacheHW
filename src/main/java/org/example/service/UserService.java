package org.example.service;

import org.example.exeption.BadRequestException;
import org.example.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findById(Long id) throws BadRequestException;

    List<UserDto> getAll();

    UserDto create(UserDto dto);

    UserDto update( UserDto dto);

    boolean delete(Long id);
}
