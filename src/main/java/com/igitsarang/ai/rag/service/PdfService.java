package com.igitsarang.ai.rag.service;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class PdfService {

    public String extractTextFromPdfUrl(String pdfUrl) {
        try {
            File file = downloadPdf(pdfUrl);

            // 1. Try normal extraction
            String text = extractUsingPdfBox(file);

            if (text != null && !text.trim().isEmpty()) {
                return text;
            }

            // 2. Fallback to OCR
            return extractUsingOCR(file);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private File downloadPdf(String url) throws Exception {
        File file = File.createTempFile("igit_", ".pdf");
        try (var in = URI.create(url).toURL().openStream()) {
            java.nio.file.Files.copy(in, file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return file;
    }

    private String extractUsingPdfBox(File file) throws Exception {
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractUsingOCR(File file) throws Exception {

        try (PDDocument document = Loader.loadPDF(file)) {

            PDFRenderer renderer = new PDFRenderer(document);

            ITesseract tesseract = new Tesseract();

            tesseract.setDatapath("/usr/share/tesseract-ocr/5/tessdata");

            tesseract.setLanguage("eng");

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < document.getNumberOfPages(); i++) {

                BufferedImage image = renderer.renderImageWithDPI(i, 300);

                String pageText = tesseract.doOCR(image);

                result.append(pageText).append("\n");
            }

            return result.toString();
        }
    }
}