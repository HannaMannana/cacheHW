package org.example.dao;

import org.example.entity.User;

import java.util.List;

public interface UserDao {
    User findById(Long id);

    List<User> findAll();

    User create(User user);

    User update(User user);

    boolean delete(Long id);
}
