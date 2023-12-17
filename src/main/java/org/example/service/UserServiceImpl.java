package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.example.proxy.CachingUserDaoProxy;
import org.example.service.dto.UserDto;
import org.example.service.mapper.Mapper;
import org.example.service.mapper.MapperImpl;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = CachingUserDaoProxy.getInstance();
    private final Mapper mapper = MapperImpl.getInstance();
    private static UserServiceImpl instance;

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    /**
     * Ищет пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return маппит найденного пользователя DTO в текущего пользователя
     */
    @Override
    public UserDto findById(Long id) {
        User user = userDao.findById(id);
        return mapper.toDto(user);
    }

    /**
     * Возвращает всех существующий пользователей
     *
     * @return маппит лист с информацией о пользователях
     */
    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.findAll();

        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            UserDto dto = mapper.toDto(user);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Создаёт нового пользователя из DTO
     *
     * @param dto DTO с информацией о создании
     * @return маппит созданного пользователя DTO в текущего пользователя
     */
    @Override
    public UserDto create(UserDto dto) {
        User user = userDao.create(mapper.toEntity(dto));
        return mapper.toDto(user);

    }

    /**
     * Обновляет уже существующего пользователя из информации полученной в DTO
     *
     * @param dto DTO с информацией для обновления
     * @return маппит обновленного пользователя DTO в текущего пользователя
     */
    @Override
    public UserDto update(UserDto dto) {
        User user = userDao.update(mapper.toEntity(dto));
        return mapper.toDto(user);
    }

    /**
     * Удаляет существующего пользователя
     *
     * @param id идентификатор пользователя для удаления
     */
    @Override
    public boolean delete(Long id) {
        boolean isDeleted = userDao.delete(id);
        if (!isDeleted) {
            return false;
        } else {
            return true;
        }
    }

}
