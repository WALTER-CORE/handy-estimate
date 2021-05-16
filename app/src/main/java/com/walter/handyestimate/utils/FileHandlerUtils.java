package com.walter.handyestimate.utils;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateTable;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandlerUtils {

    private static Logger logger = Logger.getLogger(FileHandlerUtils.class.getName());
    public static final int HEADING_FONT_SIZE = 20;
    public static final int HEADING_SPACING_AFTER = 500;
    public static final int PARAGRAPH_FONT_SIZE = 12;
    public static final int DEFAULT_SPACING_AFTER = 2;

    /**
     * Take a map of strings and format them on a file to print them
     * accordingly.
     *
     * @param estimate Estimate DTO with estimate information to write to the file.
     * @param filePath file path to write to.
     * */
    public static void writeEstimateToFile(Estimate estimate, String filePath) throws IOException {
        // Create a blank document
        XWPFDocument document = new XWPFDocument();

        // Create a file output stream connection
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            // Write the heading at the top of the page
            writeEstimateHeading(document);
            // Write the description right below the heading
            writeEstimateDescription(document, estimate.getEstimateDescription());
            // Write the id of the estimate and the date
            writeEstimateMiscDetails(document, estimate.getEstimateId(), estimate.getDate());
            writeCompanyDetails(document, estimate.getCompanyName(), estimate.getCompanyAddress());
            writeCustomerDetails(document, estimate.getCustomerName(), estimate.getCustomerAddress());
            // TODO: Add default section spacing.

            // Write content set using XWPF classes available
            document.write(outputStream);
            outputStream.close();
            document.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception when writing to the filepath: " + filePath, e);
        }
    }

    private static void writeEstimateHeading(XWPFDocument document) {
        XWPFRun headingRun = getHeaderRun(document);
        headingRun.setBold(true);
        headingRun.setText("Handy Estimate");
    }

    private static void writeEstimateDescription(XWPFDocument document, String description) {
        XWPFRun descriptionRun = getParagraphRun(document);
        descriptionRun.setText(description);
    }

    private static void writeEstimateMiscDetails(XWPFDocument document, UUID estimateId, LocalDate date) {
        XWPFRun estimateIdRun = getParagraphRun(document);
        estimateIdRun.setText("Estimate ID: " + estimateId);

        XWPFRun dateRun = getParagraphRun(document);
        dateRun.setText("Date: " + date.toString());
    }

    private static void writeCompanyDetails(XWPFDocument document, String name, String address) {
        XWPFRun run = getParagraphRun(document);
        run.setText("From: ");
        writeEstimateNameAndAddress(run, name, address);
    }

    private static void writeCustomerDetails(XWPFDocument document, String name, String address) {
        XWPFRun run = getParagraphRun(document);
        run.setText("To: ");
        writeEstimateNameAndAddress(run, name, address);
    }

    private static void writeEstimateNameAndAddress(XWPFRun run, String name, String address) {
        run.addBreak();
        run.setText(name);
        run.addBreak();
        run.setText(address);
    }


    private static XWPFRun getHeaderRun(XWPFDocument document) {
        XWPFRun run = getSpacedParagraph(document, HEADING_SPACING_AFTER).createRun();
        run.setFontSize(HEADING_FONT_SIZE);

        return run;
    }

    private static XWPFRun getParagraphRun(XWPFDocument document) {
        XWPFRun run = getSpacedParagraph(document, DEFAULT_SPACING_AFTER).createRun();
        run.setFontSize(PARAGRAPH_FONT_SIZE);

        return run;
    }

    private static XWPFParagraph getSpacedParagraph(XWPFDocument document, int spacing) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(spacing);

        return paragraph;
    }

    // TODO: Implement this
    private static void writeEstimateTable(XWPFDocument document, EstimateTable table) {
    }
}
