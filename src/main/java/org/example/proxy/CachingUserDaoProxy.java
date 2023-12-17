package org.example.proxy;

import lombok.RequiredArgsConstructor;
import org.example.cache.Cache;
import org.example.config.DataSource;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.User;

import java.util.List;

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
    public User findById(Long id) {
        User user = cache.get(id);
        if (user != null) {
            return user;
        }
        user = userDao.findById(id);
        cache.put(user.getId(), user);
        return cache.get(id);
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
