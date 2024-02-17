package com.d_d.aifoodideageneratord_d.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;

public class PdfExportService {

    public void exportRecipeToPdf(String recipeContent, File file) throws Exception {
        PdfWriter writer = new PdfWriter(file.getAbsolutePath());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph(recipeContent));

        document.close();
    }
}
