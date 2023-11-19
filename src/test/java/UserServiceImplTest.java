import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import org.example.service.dto.UserDto;
import org.example.service.mapper.Mapper;
import org.example.service.mapper.MapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserServiceImplTest {
    HikariConfig config = new HikariConfig("/datasource.yml");
    HikariDataSource dataSource = new HikariDataSource(config);
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    UserDao userDao = new UserDaoImpl(jdbcTemplate, namedParameterJdbcTemplate);
    Mapper mapper = new MapperImpl();
    UserService userService = new UserServiceImpl(userDao, mapper);


    @Test
    void findByIdShouldReturnUser() throws JsonProcessingException {
        // given
        String user = "{\"id\":1,\"name\": \"Vik\",\"lastName\":\"MikkiY\",\"email\": \"Mikki@gmail.com\",\"password\":\"12400aPWE1\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto expected = objectMapper.readValue(user, UserDto.class);

        // when
        UserDto actual = userService.findById(1L);

        // then
        assertEquals(expected, actual);

    }

    @Test
    void createShouldReturnUser() throws JsonProcessingException {
        // given
        String user = "{\"name\": \"Jon\",\"lastName\":\"Doe\",\"email\": \"Doe@gmail.com\",\"password\":\"121qoaPWE1\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto expected = objectMapper.readValue(user, UserDto.class);

        // when
        UserDto actual = userService.create(expected);

        // then
        assertEquals(expected, actual);

    }

    @Test
    void updateShouldReturnNewUser() throws JsonProcessingException {
        // given
        Long id = userService.getAll().stream().max(Comparator.comparing(UserDto::getId)).get().getId();

        String user = "{\"id\":" + id + ",\"name\": \"John\",\"lastName\":\"Dolby\",\"email\": \"Dolby@gmail.com\",\"password\":\"12400aPWE1\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto expected = objectMapper.readValue(user, UserDto.class);

        // when
        UserDto actual = userService.update(expected);

        // then
        assertEquals(expected, actual);
    }


    @Test
    void delete() {
        // given
        Long id = userService.getAll().stream().max(Comparator.comparing(UserDto::getId)).get().getId();

        boolean expected = true;

        // when
        boolean actual = userService.delete(id);

        // then
        assertEquals(expected, actual);

    }

    @Test
    void getAllShouldReturnListOfUsers() throws JsonProcessingException {
        // given
        String users = "[{\"id\":46,\"name\": \"Harry\",\"lastName\":\"Potter\",\"email\": \"Potter@gmail.com\",\"password\":\"141486QWE1\"}," +
                "{\"id\":47,\"name\": \"Ron\",\"lastName\":\"Wiesley\",\"email\": \"Wiesley@gmail.com\",\"password\":\"Q14786QWE1\"}," +
                "{\"id\":48,\"name\": \"Joe\",\"lastName\":\"Mitchel\",\"email\": \"Mitchel@gmail.com\",\"password\":\"12478odTE1\"}," +
                "{\"id\":49,\"name\": \"John\",\"lastName\":\"Dolby\",\"email\": \"Dolby@gmail.com\",\"password\":\"12400aPWE1\"}," +
                "{\"id\":50,\"name\": \"Vik\",\"lastName\":\"MikkiY\",\"email\": \"MikkiY@gmail.com\",\"password\":\"12400aP01K\"}]";


        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> expected = objectMapper.readValue(users, new TypeReference<>(){
        });

        // when
        List<UserDto> actual = userService.getAll();

        // then
        assertEquals(expected, actual);
    }
}