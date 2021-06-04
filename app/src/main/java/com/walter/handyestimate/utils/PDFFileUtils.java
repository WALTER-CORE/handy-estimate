package com.walter.handyestimate.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateLineItem;
import com.walter.handyestimate.data.model.EstimateTable;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
        PDDocument myPdf = new PDDocument();
        PDPage page = new PDPage();

        currentYOffset = 750;

        myPdf.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(myPdf, page);
        writeEstimateHeading(contentStream);
        writeEstimateDescription(contentStream, estimate.getEstimateDescription());
        writeEstimateID(contentStream, estimate.getEstimateId());
        writeEstimateDate(contentStream, estimate.getDate());
        writeEstimateCompanyInfo(contentStream,estimate.getCompanyName(), estimate.getCompanyAddress());
        writeCustomerInfo(contentStream, estimate.getCustomerName(), estimate.getCustomerAddress());
        writeEstimateTable(contentStream, estimate.getEstimateTable());
        drawEstimateTable(contentStream, page, estimate.getEstimateTable(), currentYOffset - 100, 30 );
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
    //This method draws the table
    //Perhaps put contents of estimateTable into the 2d array
    //y is coordinate start
    public static void drawEstimateTable(PDPageContentStream contentStream, PDPage page, EstimateTable estimateTable,
                                         float yPosition, float margin) throws IOException {
        final int rows = estimateTable.getEstimateLineItemList().size() + 1;
        final int cols = 4;
        final float rowHeight = 20f;
        final float tableWidth = page.getMediaBox().getWidth()-(2*margin);
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth/(float)cols;
        final float cellMargin=5f;
        List<EstimateLineItem> lineItems = estimateTable.getEstimateLineItemList();
        //4 rows always
        //cols is EstimateLineItem list length

        //draw title row
        float nexty = yPosition ;
        String[] titles = {"Description", "Quantity", "Rate", "Cost"};
//        for (int i = 0; i <= rows; i++) {
        //draw the rows

        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin,nexty);
            contentStream.lineTo(margin+tableWidth,nexty);
            contentStream.stroke();
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.moveTo(nextx, yPosition);
            contentStream.lineTo(nextx,yPosition-tableHeight);
            contentStream.stroke();
            nextx += colWidth;
        }



        float textx = margin+cellMargin;
        float texty = yPosition-15;

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,14);

        //This loop prints out just the titles
        for(int i = 0; i < titles.length; i++){
                String text = titles[i];
                contentStream.beginText();
                contentStream.newLineAtOffset(textx,texty);
                contentStream.showText(text);
                contentStream.endText();
                textx += colWidth;
        }
        texty-=rowHeight;
        textx = margin+cellMargin;

        contentStream.setFont(PDType1Font.HELVETICA,12);
        for(int i = 0; i < lineItems.size(); i++){
            for(int j = 1 ; j <= 4; j++){ //1 to 3 to get different fields
                String text;
                if (i == 1) {
                    //Description
                    text = lineItems.get(i).getDescription();
                } else if (i == 2) {
                    //Quantity
                    text = Integer.toString(lineItems.get(i).getQuantity());
                } else if (i == 3) {
                    //Rate
                    text = lineItems.get(i).getRate().toString();
                } else {
                    //Cost
                    text = lineItems.get(i).getCost().toString();
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(textx,texty);
                contentStream.showText(text);
                contentStream.endText();

                textx += colWidth;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }

//        for(int j = 1 ; j <= 3; j++){ //This loop will just have to be hard coded
//            String text = content[i][j];
//            contentStream.beginText();
//            contentStream.newLineAtOffset(textx,texty);
//            contentStream.showText(text);
//            contentStream.endText();
//            textx += colWidth;
//        }

        contentStream.setFont(PDType1Font.HELVETICA_BOLD,14);
        contentStream.beginText();
        contentStream.newLineAtOffset(430, texty - 50);
        contentStream.showText("Total Cost : " + estimateTable.getTotalCost());
        contentStream.endText();
    }

    //ORIGINAL CODE
    //public static void drawTable(PDPage page, PDPageContentStream contentStream,
    //                            float y, float margin,
    //                            String[][] content) throws IOException {
    //    final int rows = content.length;
    //    final int cols = content[0].length;
    //    final float rowHeight = 20f;
    //    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
    //    final float tableHeight = rowHeight * rows;
    //    final float colWidth = tableWidth/(float)cols;
    //    final float cellMargin=5f;
    //
    //    //draw the rows
    //    float nexty = y ;
    //    for (int i = 0; i <= rows; i++) {
    //        contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
    //        nexty-= rowHeight;
    //    }
    //
    //    //draw the columns
    //    float nextx = margin;
    //    for (int i = 0; i <= cols; i++) {
    //        contentStream.drawLine(nextx,y,nextx,y-tableHeight);
    //        nextx += colWidth;
    //    }
    //
    //    //now add the text
    //    contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
    //
    //    float textx = margin+cellMargin;
    //    float texty = y-15;
    //    for(int i = 0; i < content.length; i++){
    //        for(int j = 0 ; j < content[i].length; j++){
    //            String text = content[i][j];
    //            contentStream.beginText();
    //            contentStream.moveTextPositionByAmount(textx,texty);
    //            contentStream.drawString(text);
    //            contentStream.endText();
    //            textx += colWidth;
    //        }
    //        texty-=rowHeight;
    //        textx = margin+cellMargin;
    //    }
    //}
}
