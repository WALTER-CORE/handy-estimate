package com.walter.handyestimate.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateTable;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class PDFFileUtils {

    public static final int HEADING_FONT_SIZE = 20;
    public static final int HEADING_SPACING_AFTER = 500;
    public static final int PARAGRAPH_FONT_SIZE = 12;
    public static final int DEFAULT_SPACING_AFTER = 30;
    private static int currentYOffset;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeEstimateToPDF(Estimate estimate, String filePath) throws IOException {
        FileOutputStream stream = new FileOutputStream(filePath); //need to make file in activity first to get filepathContentStreamWriter csw = new ContentStreamWriter(stream);
//        PdfDocument.PageInfo.Builder builder = new PdfDocument.PageInfo.Builder(2550, 3000, 1);
//        PdfDocument.PageInfo myPdfInfo = builder.create();
        PDDocument myPdf = new PDDocument();

//        PdfDocument.Page page1 = myPdf.startPage(myPdfInfo);
        PDPage page = new PDPage();
        currentYOffset = 750;
//        Canvas canvas = page1.getCanvas()
        //8.5 x 11 inch = 2550 x 3300 pixel
        //code to insert text and table
        myPdf.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(myPdf, page);
        writeEstimateHeading(contentStream);
        writeEstimateDescription(contentStream, estimate.getEstimateDescription());
        writeEstimateID(contentStream, estimate.getEstimateId());
        writeEstimateDate(contentStream, estimate.getDate());
        writeEstimateCompanyInfo(contentStream,estimate.getCompanyName(), estimate.getCompanyAddress());
        writeCustomerInfo(contentStream, estimate.getCustomerName(), estimate.getCustomerAddress());
        writeEstimateTable(contentStream, estimate.getEstimateTable());
        contentStream.close();
        myPdf.save(stream);
        stream.close();
        myPdf.close();
    }

    public static void writeEstimateHeading(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.showText("Handy Estimate");
        contentStream.endText();
    }

    public static void writeEstimateDescription(PDPageContentStream contentStream, String estimateDescription) throws IOException {
        contentStream.beginText();
        currentYOffset -= 60;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText(estimateDescription);
        contentStream.endText();
    }

    private static void writeEstimateID(PDPageContentStream contentStream, UUID estimateID) throws IOException {
        contentStream.beginText();
        currentYOffset -= 100;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText("Estimate ID: " + estimateID.toString());
        contentStream.endText();
    }

    private static void writeEstimateDate(PDPageContentStream contentStream, LocalDate estimateDate) throws IOException {
        contentStream.beginText();
        currentYOffset -= 20;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText("Date: " + estimateDate.toString());
        contentStream.endText();
    }

    public static void writeEstimateCompanyInfo(PDPageContentStream contentStream, String companyName, String companyAddress) throws IOException {
        contentStream.beginText();
        currentYOffset -= 80;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText("From: ");
        contentStream.endText();

        contentStream.beginText();
        currentYOffset -= 20;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText(companyName);
        contentStream.endText();

        contentStream.beginText();
        currentYOffset -= 20;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText(companyAddress);
        contentStream.endText();
    }

    public static void writeCustomerInfo(PDPageContentStream contentStream, String customerName, String customerAddress) throws IOException {
        contentStream.beginText();
        currentYOffset -= 80;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText("To: ");
        contentStream.endText();

        contentStream.beginText();
        currentYOffset -= 20;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText(customerName);
        contentStream.endText();

        contentStream.beginText();
        currentYOffset -= 20;
        contentStream.newLineAtOffset(25, currentYOffset);
        contentStream.setFont(PDType1Font.HELVETICA, 14);
        contentStream.showText(customerAddress);
        contentStream.endText();
    }

    public static void writeEstimateTable(PDPageContentStream contentStream, EstimateTable estimateTable) throws IOException {

    }



}
