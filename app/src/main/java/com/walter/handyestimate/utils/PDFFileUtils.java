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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeEstimateToPDF(Estimate estimate, String filePath) throws IOException {
        FileOutputStream stream = new FileOutputStream(filePath); //need to make file in activity first to get filepathContentStreamWriter csw = new ContentStreamWriter(stream);
//        PdfDocument.PageInfo.Builder builder = new PdfDocument.PageInfo.Builder(2550, 3000, 1);
//        PdfDocument.PageInfo myPdfInfo = builder.create();
        PDDocument myPdf = new PDDocument();

//        PdfDocument.Page page1 = myPdf.startPage(myPdfInfo);
        PDPage page = new PDPage();

//        Canvas canvas = page1.getCanvas()

        //code to insert text and table
        PDPageContentStream contentStream = new PDPageContentStream(myPdf, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.moveTo(100, 700);
        contentStream.showText(estimate.getCompanyAddress());
        contentStream.endText();

        myPdf.save(stream);
        stream.close();
        myPdf.close();
    }
}
