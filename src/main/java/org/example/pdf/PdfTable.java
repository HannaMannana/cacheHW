package org.example.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserDao;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


@Component
@RequiredArgsConstructor
public class PdfTable {

    private final UserDao userDao;


    /**
     * Создает PDF файл с таблицей
     */
    public void createTable() {
        float[] pointColumnWidths = {50F, 150F, 150F, 250F, 200F};

        try {
            Document document = new Document(PageSize.A4, 10, 0, 350, 0);
            File file = new File("PDF/User List" + ".pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            background(writer, document);
            this.addTable(document, pointColumnWidths);
            document.close();
            System.out.println("Pdf created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Создает таблицу
     *
     * @param document файл в который добавляем таблицу
     * @param size     размер колонок
     */
    private void addTable(Document document, float[] size) throws Exception {
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


    /**
     * Задает параметры для колонок
     *
     * @param table  таблица
     * @param string название колонки
     */
    private static void addTableFields(PdfPTable table, String string) {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL);
        Paragraph paragraph = new Paragraph(string, font);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);
    }


    /**
     * Задает параметры для заголовка
     *
     * @param table  таблица
     * @param string название заголовка
     */
    private static void addTitle(PdfPTable table, String string) {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20,
                Font.BOLD);
        Paragraph paragraph = new Paragraph(string, font);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    /**
     * Задает фон
     *
     * @param writer   добавляет jpg
     * @param document название заголовка
     */
    public static void background(PdfWriter writer, Document document) throws DocumentException, IOException {
        Image background = Image.getInstance("/images/Clever.jpg");
        float width = document.getPageSize().getWidth();
        float height = document.getPageSize().getHeight();
        writer.getDirectContentUnder()
                .addImage(background, width, 0, 0, height, 0, 0);
    }



}
