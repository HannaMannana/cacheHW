package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.proxy.CachingUserDaoProxy;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import org.example.service.dto.UserDto;
import org.example.service.mapper.Mapper;
import org.example.service.mapper.MapperImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Properties;

import static org.example.validation.Valid.valid;

public class Main {
    public static void main(String[] args) throws JsonProcessingException, SQLException, DatabaseException {

        HikariConfig config = new HikariConfig("/datasource.yml");
        HikariDataSource dataSource = new HikariDataSource(config);

        Properties properties = new Properties();

        java.sql.Connection connection = dataSource.getConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        try (Liquibase liquibase = new liquibase.Liquibase("changeLog/db.changelog.yml", new ClassLoaderResourceAccessor(), database)) {
            properties.forEach((key, value) -> liquibase.setChangeLogParameter(Objects.toString(key), value));
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        UserDao userDao = new UserDaoImpl(jdbcTemplate, namedParameterJdbcTemplate);
        Mapper mapper = new MapperImpl();
        UserDao cacheUserDao = new CachingUserDaoProxy(userDao);
        UserService userService = new UserServiceImpl(cacheUserDao, mapper);

        ObjectMapper objectMapper = new ObjectMapper();

        String userToSave = "{\"name\": \"Pitter\",\"lastName\":\"Pen\",\"email\": \"Pen@gmail.com\",\"password\":\"124781HWE1\"}";
        String userForUpdate = "{\"name\": \"Pitter\",\"lastName\":\"Pirker\",\"email\": \"Parker@gmail.com\",\"password\":\"89n1mnOWEa\"}";

        System.out.println(userService.findById(48L));


        System.out.println(userService.findById(1L));


        UserDto userDtoToSave = objectMapper.readValue(userToSave, UserDto.class);
        UserDto savedUser = userService.create(valid(userDtoToSave));
        System.out.println(savedUser);


        Long id = userService.getAll().stream().max(Comparator.comparing(UserDto::getId)).get().getId();

        UserDto userDtoForUpdate = objectMapper.readValue(userForUpdate, UserDto.class);
        userDtoForUpdate.setId(id);
        UserDto updatedUser = userService.update(valid(userDtoForUpdate));
        System.out.println(updatedUser);
        boolean isUserDeleted = userService.delete(id);
        System.out.println(isUserDeleted);


        System.out.println(userService.getAll());
    }
}