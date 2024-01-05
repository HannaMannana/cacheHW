package org.example.controller;

import org.example.exeption.BadRequestException;
import org.example.service.UserService;
import org.example.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "users", value = "/users")
public class UserController extends HttpServlet {

    @Autowired
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(
                this, config.getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (!Objects.isNull(pathInfo) && !pathInfo.replace("/", "").isBlank()) {
                String id = pathInfo.replace("/", "");

                UserDto user = userService.findById(Long.valueOf(id));

                ServletUtil.respond(user, resp);
            } else {
                String strPage = req.getQueryString().replace("page=", "");
                int page = Integer.parseInt(strPage);
                int pageSize = 2;
                List<UserDto> users = userService.getAll();
                List<UserDto> pageList = users.stream().skip((long) page * pageSize).limit(pageSize).toList();

                ServletUtil.respond(pageList, resp);
            }
        } catch (NumberFormatException numberFormatException) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServletUtil.respond(numberFormatException.getMessage(), resp);

        } catch (BadRequestException exception) {
            resp.setStatus(exception.getStatus());
            ServletUtil.respond(exception.getMessage(), resp);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ServletUtil.respond("Error", resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            UserDto user = ServletUtil.expect(UserDto.class, req);
            ServletUtil.respond(userService.create(user), resp);
        } catch (BadRequestException exception) {
            resp.setStatus(exception.getStatus());
            ServletUtil.respond(exception.getMessage(), resp);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ServletUtil.respond("Error", resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            UserDto user = ServletUtil.expect(UserDto.class, req);
            ServletUtil.respond(userService.update(user), resp);

        } catch (BadRequestException exception) {
            resp.setStatus(exception.getStatus());
            ServletUtil.respond(exception.getMessage(), resp);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ServletUtil.respond("Error", resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo();

        try {

            if (!Objects.isNull(pathInfo) && !pathInfo.replace("/", "").isBlank()) {

                String id = pathInfo.replace("/", "");
                UserDto user = userService.findById(Long.valueOf(id));
                userService.delete(user.getId());

            } else {
                throw new BadRequestException("No user id was found");
            }

        } catch (BadRequestException exception) {
            resp.setStatus(exception.getStatus());
            ServletUtil.respond(exception.getMessage(), resp);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ServletUtil.respond("Error", resp);
        }
    }

}

