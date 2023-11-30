package org.example.service.mapper;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.User;
import org.example.service.dto.UserDto;

public class MapperImpl implements Mapper{

    private static Mapper instance;

    public static Mapper getInstance() {
        if (instance == null) {
            instance = new MapperImpl() {
            };
        }
        return instance;
    }

    /**
     * Маппит текущего пользователя в DTO
     *
     * @param entity - существующий пользователь
     * @return DTO пользователя
     */
    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    /**
     * Маппит пользователя DTO
     *
     * @param dto - dto пользователь
     * @return тукущего пользователя
     */
    @Override
    public User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
