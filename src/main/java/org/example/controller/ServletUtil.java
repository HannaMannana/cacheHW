package org.example.controller;

import com.google.gson.Gson;
import org.example.exeption.BadRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtil {

    public static <T> T expect(Class<T> Class, HttpServletRequest request) throws BadRequestException {
        try {
            Gson gson = new Gson();

            return gson.fromJson(request.getReader(), Class);

        } catch (IOException e) {
            throw new BadRequestException("Invalid request payload");
        }
    }

    public static void respond(Object payload, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(payload));
    }
}
