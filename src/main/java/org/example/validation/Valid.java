package org.example.validation;

import org.example.service.dto.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valid {

    static String emailRegex = "^(.+)@(.+)$";
    static String nameRegex = "^[A-Za-z]{2,29}$";
    static String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";

    /**
     * Проверяет пользователя по указанным параметрам
     *
     * @param dto DTO с информацией о создании
     * @return проверенного пользователя DTO
     */
    public static UserDto valid(UserDto dto) {

        if (dto.getName() == null || dto.getName().isBlank() && isValid(dto.getName(), nameRegex)) {
            throw new RuntimeException("Name is required");
        }
        if (dto.getLastName() == null || dto.getLastName().isBlank() && isValid(dto.getLastName(), nameRegex)) {
            throw new RuntimeException("LastName is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank() && isValid(dto.getEmail(), emailRegex)) {
            throw new RuntimeException("Email is required");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank() && isValid(dto.getPassword(), passwordRegex)) {
            throw new RuntimeException("Password is required");
        }
        return dto;
    }


    public static boolean isValid(String string, String regex) {
        Pattern p = Pattern.compile(regex);
        if ("".equals(string)) {

            return false;
        } else {
            Matcher m = p.matcher(string);
            if (!m.matches()) {

            }
            return m.matches();
        }
    }
}




