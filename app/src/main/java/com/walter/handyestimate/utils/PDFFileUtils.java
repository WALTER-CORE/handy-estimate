package com.walter.handyestimate.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.walter.handyestimate.data.model.Estimate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFFileUtils {

    public static final int HEADING_FONT_SIZE = 20;
    public static final int HEADING_SPACING_AFTER = 500;
    public static final int PARAGRAPH_FONT_SIZE = 12;
    public static final int DEFAULT_SPACING_AFTER = 1000;
    private static int currentYOffset;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeEstimateToPDF(Estimate estimate, String filePath) throws IOException {
        FileOutputStream stream = new FileOutputStream(filePath); //need to make file in activity first to get filepathContentStreamWriter csw = new ContentStreamWriter(stream);
//        PdfDocument.PageInfo.Builder builder = new PdfDocument.PageInfo.Builder(2550, 3000, 1);
//        PdfDocument.PageInfo myPdfInfo = builder.create();
        PDDocument myPdf = new PDDocument();

//        PdfDocument.Page page1 = myPdf.startPage(myPdfInfo);
        PDPage page = new PDPage();
        currentYOffset = 500;
//        Canvas canvas = page1.getCanvas()
        //8.5 x 11 inch = 2550 x 3300 pixel
        //code to insert text and table
        myPdf.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(myPdf, page);
        writeEstimateHeading(contentStream);
        writeEstimateCompanyAddress(contentStream, estimate.getCompanyAddress());
        contentStream.close();
        myPdf.save(stream);
        stream.close();
        myPdf.close();
    }

    public static void writeEstimateHeading(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(5, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.showText("Handy Estimate");
        contentStream.endText();
    }

    public static void writeEstimateCompanyAddress(PDPageContentStream contentStream, String companyAddress) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(5, currentYOffset - DEFAULT_SPACING_AFTER);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.showText(companyAddress);
        contentStream.endText();
    }

}
