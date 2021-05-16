package com.walter.handyestimate.utils;

import com.walter.handyestimate.data.ContentType;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class FileHandlerUtils {


    /**
     * Take a map of strings and format them on a file to print them
     * accordingly.
     *
     * @param input Map of content we should output onto a file.
     * */
    public void writeToFile(Map<ContentType, String> input, String filePath) throws IOException {
        // Create a blank document
        XWPFDocument xwpfdocument = new XWPFDocument();

        // Create a blank file at C:
        File file = new File(filePath);

        // Create a file output stream connection
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            /* Create a new paragraph using the document */

            // CreateParagraph() method is used
            // to instantiate a new paragraph
            XWPFParagraph para = xwpfdocument.createParagraph();

            // CreateRun method appends a new run to the
            // paragraph created
            XWPFRun xwpfrun = para.createRun();

            // SetText sets the text to the run
            // created using XWPF run
            xwpfrun.setText(
                    "Geeks for Geeks is a computer science portal which aims "
                            + "to provide all in one platform for learning and "
                            + "practicing.We can learn multiple programming languages here. ");

            // Write content set using XWPF classes available
            xwpfdocument.write(outputStream);

            // Close connection
            outputStream.close();
        }
    }

}
