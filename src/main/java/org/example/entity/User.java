package org.example.entity;

import lombok.Data;

@Data
public class User {

    /**
     * Идентификатор продукта (генерируется базой)
     */
    public Long id;
    /**
     * Имя пользователя (не может быть null или пустым)
     */
    public String name;
    /**
     * Фамилия пользователя (не может быть null или пустым)
     */
    public String lastName;
    /**
     * Электронная почта пользователя (не может быть null или пустым, обязательно должна содержать "@")
     */
    public String email;
    /**
     * Пароль пользователя (не может быть null или пустым, макс. -  10 символов(анг. и без пробелов))
     */
    public String password;


}
