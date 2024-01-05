package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exeption.BadRequestException;
import org.example.repository.UserDao;
import org.example.service.dto.UserDto;
import org.example.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapper mapper;

    /**
     * Ищет пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return маппит найденного пользователя DTO в текущего пользователя
     */
    @Override
    public UserDto findById(Long id) throws BadRequestException {
        Optional<User> user = userDao.findById(id);

        return mapper.toDto(user.orElseThrow(() -> new BadRequestException("USEER NOT FOUND")));
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


    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {

        if(pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() < fromIndex){
            return Collections.emptyList();
        }

        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

}
