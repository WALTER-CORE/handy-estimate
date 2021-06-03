package com.walter.handyestimate.utils;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.walter.handyestimate.data.model.Estimate;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFFileUtils {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeEstimateToPDF(Estimate estimate, String filePath) throws IOException {
        FileOutputStream stream = new FileOutputStream(filePath); //need to make file in activity first to get filepath

        PdfDocument.PageInfo.Builder builder = new PdfDocument.PageInfo.Builder(2550, 3000, 1);
        PdfDocument.PageInfo myPdfInfo = builder.create();
        PdfDocument myPdf = new PdfDocument();
        Paint myPaint = new Paint();
        PdfDocument.Page page1 = myPdf.startPage(myPdfInfo);
        Canvas canvas = page1.getCanvas();

        //code to insert text and table







        myPdf.writeTo(stream);
        stream.close();
        myPdf.finishPage(page1);
        myPdf.close();
    }
}
