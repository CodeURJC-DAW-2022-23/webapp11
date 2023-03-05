package com.techmarket.app.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.techmarket.app.model.Purchase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class PDFService {

    public static void generateInvoice(HttpServletResponse response, Map<String, Object> data) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

        // Generate the invoice
        PdfDocument pdf = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document document = new Document(pdf, PageSize.A4);

        // Add the invoice data
        Purchase purchase = (Purchase) data.get("purchase");
        document.add(new Paragraph("Purchase ID: " + purchase.getPurchaseId()));
        document.add(new Paragraph("Purchase Date: " + purchase.getTimestamp()));
        document.add(new Paragraph("Customer Name: " + purchase.getUser().getFirstName() + " " + purchase.getUser().getLastName()));
        document.add(new Paragraph("Customer Email: " + purchase.getUser().getEmail()));
        document.add(new Paragraph("Shipping Address: " + purchase.getAddress()));
        document.add(new Paragraph("Product details: " + purchase.getProduct().getProductName()));
        document.add(new Paragraph("Total: " + purchase.getProduct().getProductPrice() + "€"));
        document.add(new Paragraph("Payment method: " + purchase.getPaymentMethod()));
        document.add(new Paragraph("Thank you for your purchase!"));
        document.add(new Paragraph("3TechMarket Team"));

        document.close();
    }
}
