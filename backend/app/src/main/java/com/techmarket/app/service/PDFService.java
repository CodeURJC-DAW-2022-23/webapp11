package com.techmarket.app.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.techmarket.app.model.Purchase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

@Service
public class PDFService {

    public static void generateInvoice(HttpServletResponse response, Purchase purchase) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

        // Take our Mustache template from /resources/invoice.html and convert it to a PDF
        ClassLoader classLoader = PDFService.class.getClassLoader();  // Get the class loader to access the resources folder
        Reader template = new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream("templates/invoice.html")));  // Get the invoice.html file from the resources folder
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(template, "pdf-template");  // Compile the template into a Mustache object
        StringWriter writer = new StringWriter();
        mustache.execute(writer, Map.of("purchase", purchase)).flush();  // Execute the template with the purchase object
        String html = writer.toString();

        // Generate the invoice
        PdfDocument pdf = new PdfDocument(new PdfWriter(response.getOutputStream()));
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(html, pdf, converterProperties);  // Convert the HTML to a PDF
        pdf.close();
    }
}
