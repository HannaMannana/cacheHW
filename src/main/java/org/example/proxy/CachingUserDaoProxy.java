package org.example.proxy;

import org.example.cache.Cache;
import org.example.repository.UserDao;
import org.example.repository.UserDaoImpl;
import org.example.entity.User;

import java.util.List;
import java.util.Optional;

public class CachingUserDaoProxy implements UserDao {

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final Cache<User> cache = new Factory<User>().createCache();
    private static CachingUserDaoProxy instance;

    public static CachingUserDaoProxy getInstance() {
        if (instance == null) {
            instance = new CachingUserDaoProxy();
        }
        return instance;
    }


    /**
     * Ищет в кэш пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return возвращает пользователя найденого в кэш
     */
    @Override
    public Optional<User> findById(Long id) {
        User user = cache.get(id);
        if (user != null) {
            return Optional.of(user);
        }

        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isPresent()) {
            User foundedUser = optionalUser.get();
            cache.put(foundedUser.getId(), foundedUser);
            return Optional.ofNullable(cache.get(id));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Ищет всех пользователей в БД
     *
     * @return список найденных пользователей
     */
    @Override
    public List<User> findAll() { // как закидывать, если есть ограничения по кэш?
        return userDao.findAll();
    }

    /**
     * Сохраняет пользователя в кэш
     *
     * @param user сохраняемый пользователь
     * @return возвращает пользователя сохраненного в кэш
     */
    @Override
    public User create(User user) {
        userDao.create(user);
        cache.put(user.getId(), user);
        return cache.get(user.getId());
    }

    /**
     * Обновляет пользователя в кэш
     *
     * @param user сохраняемый пользователь
     * @return возвращает пользователя обнавленного в кэш
     */
    @Override
    public User update(User user) {
        userDao.update(user);
        cache.put(user.getId(), user);
        return cache.get(user.getId());
    }

    /**
     * Удаляет пользователя из кэша по идентификатору
     *
     * @param id идентификатор пользователя
     */
    @Override
    public boolean delete(Long id) {
        userDao.delete(id);
        if (cache.get(id) != null) {
            cache.delete(cache.get(id).getId());
        }
        return true;
    }

}
