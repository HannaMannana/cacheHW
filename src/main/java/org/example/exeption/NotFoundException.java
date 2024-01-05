package org.example.exeption;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class NotFoundException extends Exception {
    private final int status = HttpServletResponse.SC_NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }
}
