package org.example.repository;

import org.example.config.DataSource;
import org.example.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    public static final String FIND_BY_ID = "SELECT  u.id, u.name_user, u.last_name, u.email, u.password " +
            "FROM users u WHERE u.id = ?";
    public static final String FIND_ALL = "SELECT id, name_user, last_name, email, password " +
            "FROM users";
    public static final String SAVE = "INSERT INTO users (name_user, last_name, email, password)" +
            "VALUES ( ?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET name_user = :name, last_name = :last_name, email = :email, password = :password " +
            " WHERE id = :id";
    public static final String DELETE = "DELETE FROM users WHERE id = ?";


    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSource.getInstance());

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DataSource.getInstance());

    private static UserDao instance;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl() {
            };
        }
        return instance;
    }

    private User mapRow(ResultSet resultSet, int number) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name_user"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    private static PreparedStatement getPreparedStatement(User user, PreparedStatement statement) throws SQLException {

        statement.setString(1, user.getName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        return statement;
    }

    /**
     * Ищет в БД пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return возвращает пользователя найденого в БД
     */
    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Ищет всех пользователей в БД
     *
     * @return список найденных пользователей
     */
    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, this::mapRow);
    }

    /**
     * Сохраняет пользователя в БД
     *
     * @param user сохраняемый пользователь
     * @return сохранённый пользователь
     */
    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int id = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            return getPreparedStatement(user, statement);
        }, keyHolder);
        return findById((long) id).orElseThrow();
    }

    /**
     * Обновляет пользователя в БД
     *
     * @param user сохраняемый пользователь
     * @return обновленный пользователь
     */
    @Override
    public User update(User user) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("last_name", user.getLastName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("id", user.getId());

        int update = namedParameterJdbcTemplate.update(UPDATE, params);
        if (update == 0) {
            throw new RuntimeException("Could not update book" + user);
        }
        return findById(user.getId()).orElseThrow();
    }

    /**
     * Удаляет пользователя из памяти по идентификатору
     *
     * @param id идентификатор пользователя
     */
    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE, id) == 1;
    }
}
