package org.example.entity;

import lombok.Data;

@Data
public class User {

    /**
     * Идентификатор продукта (генерируется базой)
     */
    private Long id;
    /**
     * Имя пользователя (не может быть null или пустым)
     */
    private String name;
    /**
     * Фамилия пользователя (не может быть null или пустым)
     */
    private String lastName;
    /**
     * Электронная почта пользователя (не может быть null или пустым, обязательно должна содержать "@")
     */
    private String email;
    /**
     * Пароль пользователя (не может быть null или пустым, макс. -  10 символов(анг. и без пробелов))
     */
    private String password;


}
