package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import liquibase.exception.DatabaseException;
import org.example.config.Liquibase;
import org.example.exeption.BadRequestException;
import org.example.pdf.PdfTable;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException, SQLException, DatabaseException, BadRequestException, DocumentException, FileNotFoundException {

        Liquibase.init();
//        UserService userService = UserServiceImpl.getInstance();
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String userToSave = "{\"name\": \"Pitter\",\"lastName\":\"Pen\",\"email\": \"Pen@gmail.com\",\"password\":\"124781HWE1\"}";
//        String userForUpdate = "{\"name\": \"Pitter\",\"lastName\":\"Pirker\",\"email\": \"Parker@gmail.com\",\"password\":\"89n1mnOWEa\"}";
//
//        System.out.println(userService.findById(46L));
//
//
//        UserDto userDtoToSave = objectMapper.readValue(userToSave, UserDto.class);
//        UserDto savedUser = userService.create(valid(userDtoToSave));
//        System.out.println(savedUser);
//
//
//        Long id = userService.getAll(1,10).stream().max(Comparator.comparing(UserDto::getId)).get().getId();
//
//        UserDto userDtoForUpdate = objectMapper.readValue(userForUpdate, UserDto.class);
//        userDtoForUpdate.setId(id);
//        UserDto updatedUser = userService.update(valid(userDtoForUpdate));
//        System.out.println(updatedUser);
//
//        boolean isUserDeleted = userService.delete(id);
//        System.out.println(isUserDeleted);
//
//
//        System.out.println(userService.getAll(1, 10));
//
        PdfTable.createTable();

    }
}