package org.example.service.dto;

import lombok.Data;

@Data
public class UserDto {
    /**
     * Идентификатор продукта (берется из базы)
     */
    private Long id;
    /**
     * Имя пользователя для маппинга(не может быть null или пустым)
     */
    private String name;
    /**
     * Фамилия пользователя для маппинга(не может быть null или пустым)
     */
    private String lastName;
    /**
     * Электронная почта пользователя для маппинга(не может быть null или пустым, обязательно должна содержать "@")
     */
    private String email;
    /**
     * Пароль пользователя для маппинга(не может быть null или пустым, макс. -  10 символов(анг. и без пробелов))
     */
    private String password;


}
