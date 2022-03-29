package com.sd4.Builder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.model.Category;
import com.sd4.model.Style;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.persistence.Convert;

public class BuildPDF {

    Beer beer;
    Brewery brewery;
    Category category;
    Style style;

    public BuildPDF(Beer beer, Brewery brewery, Category category, Style style) {
        this.beer = beer;
        this.brewery = brewery;
        this.category = category;
        this.style = style;
    }

    private static final Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);

    public File generatePdfReport() throws DocumentException, IOException {
        File file = File.createTempFile("report", ".pdf");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            Document document = new Document();
            PdfWriter.getInstance(document, fileOutputStream);
            document.open();
            addBeer(document);
            document.close();
            return file;
        }

    }

    private void addBeer(Document document) throws DocumentException {
        Paragraph p1 = new Paragraph();
        String sell = beer.getSell_price().toString();
        String avb = beer.getAbv().toString();

        p1.add(new Paragraph(beer.getName(), COURIER));
        p1.add(new Paragraph("Description : " + beer.getDescription(), COURIER));

        p1.add(new Paragraph(" "));
        p1.add(new Paragraph("Price : " + sell, COURIER));

        p1.add(new Paragraph(" "));
        p1.add(new Paragraph("AVB : " + avb, COURIER));

        p1.add(new Paragraph(" "));
        p1.add(new Paragraph("Brewery Name : " + brewery.getName(), COURIER));

        p1.add(new Paragraph(" "));
        p1.add(new Paragraph("WebSite : " + brewery.getWebsite(), COURIER));

        p1.add(new Paragraph(" "));
        p1.add(new Paragraph("Category Name : " + category.getCat_name(), COURIER));

        p1.add(new Paragraph(" "));
        p1.add(new Paragraph("Style : " + style.getStyle_name(), COURIER));

        p1.setAlignment(Element.ALIGN_CENTER);

        document.add(p1);
    }

}
