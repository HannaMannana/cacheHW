package org.example.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class PdfTable {


    public static void createTable() {
        float[] pointColumnWidths = {50F, 150F, 150F, 250F, 200F};

        try {
            Document document = new Document(PageSize.A4, 10, 0, 350, 0);
            File file = new File("PDF/User List" + ".pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            onEndPage(writer, document);
            addTable(document, pointColumnWidths);
            document.close();
            document.close();
            System.out.println("Pdf created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addTable(Document document, float[] size) throws Exception {
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> users = userDao.findAll();
        Field[] fields = User.class.getFields();
        PdfPTable table = new PdfPTable(size);
        PdfPTable titleTable = new PdfPTable(1);
        addTitle(titleTable, "Users");
        for (Field field : fields) {
            String fieldName = field.getName();
            addTableFields(table, fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        }

        for (User user : users) {
            for (Field field : fields) {
                addTableFields(table, String.valueOf(field.get(user)));
            }
        }
        document.add(titleTable);
        document.add(table);

    }


    private static void addTableFields(PdfPTable table, String string) {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL);
        Paragraph paragraph = new Paragraph(string, font);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);
    }


    private static void addTitle(PdfPTable table, String string) {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20,
                Font.BOLD);
        Paragraph paragraph = new Paragraph(string, font);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }


    private static void onEndPage(PdfWriter writer, Document document) throws DocumentException, IOException {
        Image background = Image.getInstance("src/main/resources/images/Clever.jpg");
        float width = document.getPageSize().getWidth();
        float height = document.getPageSize().getHeight();
        writer.getDirectContentUnder()
                .addImage(background, width, 0, 0, height, 0, 0);
    }


}
