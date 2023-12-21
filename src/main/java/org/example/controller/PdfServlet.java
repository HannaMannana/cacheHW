package org.example.controller;

import org.example.pdf.PdfUserInfo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(value = "/pdf/*", name = "PdfServlet")
public class PdfServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        try {

            if (!Objects.isNull(pathInfo) && !pathInfo.replace("/", "").isBlank()) {

                String id = request.getPathInfo().replace("/", "");
                PdfUserInfo.makePdf(request, response, Long.valueOf(id));
            }

        } catch (NumberFormatException numberFormatException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServletUtil.respond(numberFormatException.getMessage(), response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ServletUtil.respond("Error", response);
        }


    }
}
