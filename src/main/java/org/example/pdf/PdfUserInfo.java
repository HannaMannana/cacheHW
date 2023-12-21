package org.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.entity.User;
import org.example.repository.UserDao;
import org.example.repository.UserDaoImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

public class PdfUserInfo {

    public static void makePdf(HttpServletRequest request, HttpServletResponse response, Long id) {
        try {
            UserDao userDao = new UserDaoImpl();
            Document document = new Document(PageSize.A4, 10, 0, 350, 0);

            User user = userDao.findById(id).orElseThrow();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();

            document.add(new Paragraph("Name:" + " " + user.getName()));
            document.add(new Paragraph("Last Name:" + " " + user.getLastName()));
            document.add(new Paragraph("Email:" + " " + user.getEmail()));

            document.close();

            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();

        } catch (Exception exception) {
            System.out.println("Error in " + PdfUserInfo.class.getName() + "\n" + exception);
        }
    }


}
