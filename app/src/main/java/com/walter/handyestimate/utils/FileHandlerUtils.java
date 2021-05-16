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

    Logger logger = Logger.getLogger(this.getClass().getName());
    private static final int HEADING_FONT_SIZE = 40;
    private static final int PARAGRAPH_FONT_SIZE = 40;
    private static final int DEFAULT_SPACING_AFTER = 2;

    /**
     * Take a map of strings and format them on a file to print them
     * accordingly.
     *
     * @param estimate Estimate DTO with estimate information to write to the file.
     * @param filePath file path to write to.
     * */
    public void writeEstimateToFile(Estimate estimate, String filePath) throws IOException {
        // Create a blank document
        XWPFDocument document = new XWPFDocument();

        // Create a file output stream connection
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {

            // Write the heading at the top of the page
            writeEstimateHeading(document);
            // Write the description right below the heading
            writeEstimateDescription(document, estimate.getEstimateDescription());
            // The id of the estimate and the date
            writeEstimateMiscDetails(document, estimate.getEstimateId(), estimate.getDate());

            // Write content set using XWPF classes available
            document.write(outputStream);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception when writing to the filepath: " + filePath, e);
        }
    }

    private void writeEstimateHeading(XWPFDocument document) {
        XWPFRun headingRun = getHeaderRun(document);
        headingRun.setBold(true);
        headingRun.setText("Handy Estimate");
    }

    private void writeEstimateDescription(XWPFDocument document, String description) {
        XWPFRun descriptionRun = getParagraphRun(document);
        descriptionRun.setText(description);
    }

    private void writeEstimateMiscDetails(XWPFDocument document, UUID estimateId, LocalDate date) {
        XWPFRun estimateIdRun = getParagraphRun(document);
        estimateIdRun.setText("Estimate ID: " + estimateId);

        XWPFRun dateRun = getParagraphRun(document);
        dateRun.setText("Date: " + date.toString());
    }

    // TODO: Implement this
    private void writeCompanyInformation(XWPFDocument document, String description) {
        XWPFRun descriptionRun = getParagraphRun(document);
        descriptionRun.setText(description);
    }

    // TODO: Implement this
    private void writeCustomerInformation(XWPFDocument document, String description) {
        XWPFRun descriptionRun = getParagraphRun(document);
        descriptionRun.setText(description);
    }

    private XWPFRun getHeaderRun(XWPFDocument document) {
        XWPFRun run = getSpacedParagraph(document).createRun();
        run.setFontSize(HEADING_FONT_SIZE);

        return run;
    }

    private XWPFRun getParagraphRun(XWPFDocument document) {
        XWPFRun run = getSpacedParagraph(document).createRun();
        run.setFontSize(PARAGRAPH_FONT_SIZE);

        return run;
    }

    private XWPFParagraph getSpacedParagraph(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(DEFAULT_SPACING_AFTER);

        return paragraph;
    }

    // TODO: Implement this
    private void writeEstimateTable(XWPFDocument document, EstimateTable table) {
    }
}
