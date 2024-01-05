package org.example.exeption;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class BadRequestException extends Exception {
   private final int status = HttpServletResponse.SC_BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }
}
