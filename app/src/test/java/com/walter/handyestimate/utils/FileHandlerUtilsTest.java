package com.walter.handyestimate.utils;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateLineItem;
import com.walter.handyestimate.data.model.EstimateTable;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.walter.handyestimate.utils.FileHandlerUtils.DEFAULT_SPACING_AFTER;
import static com.walter.handyestimate.utils.FileHandlerUtils.HEADING_FONT_SIZE;
import static com.walter.handyestimate.utils.FileHandlerUtils.HEADING_SPACING_AFTER;
import static com.walter.handyestimate.utils.FileHandlerUtils.PARAGRAPH_FONT_SIZE;
import static com.walter.handyestimate.utils.FileHandlerUtils.writeEstimateToFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FileHandlerUtilsTest {

    /* This folder and the files created in it will be deleted after
     * tests are run, even in the event of failures or exceptions.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testWriteEstimateToFile() throws IOException {
        // TODO change this path to the temporary directory.
        String path = folder.newFolder("/estimateTestOutput").getAbsolutePath();
        path = "C:/Users/Brian Jhong/desktop/temp/estimateTestWordFile";
        String estimateDescription = "[ESTIMATE DESCRIPTION] Estimate for fence repair";
        String companyName = "[COMPANY NAME] Handy Company";
        String companyAddress = "[COMPANY ADDRESS] 1234 Sesame Street, Seattle WA 11111";
        String customerName = "[CUSTOMER NAME] Customer Name";
        String customerAddress = "[COMPANY ADDRESS] 5678 Customer Way, Seattle WA 22222";
        EstimateTable estimateTable = new EstimateTable(
                Arrays.asList(
                        new EstimateLineItem("Item 1", 1, BigDecimal.TEN),
                        new EstimateLineItem("Item 2", 2, BigDecimal.valueOf(20))
                )
        );
        Estimate estimate = new Estimate(estimateDescription, companyName, companyAddress, customerName,
                customerAddress, estimateTable);

        writeEstimateToFile(estimate, path);
        XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get(path)));

        // Assert file exists.
        assertNotNull(document);

        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<XWPFTable> tables = document.getTables();
        document.close();

        // Assert paragraphs exists.
        assertNotNull(paragraphs);
        assertFalse(paragraphs.isEmpty());

        // Assert heading content and style.
        XWPFParagraph headingParagraph = paragraphs.get(0);
        XWPFRun headingRun = headingParagraph.getRuns().get(0);

        assertEquals("Handy Estimate", headingParagraph.getText());
        assertEquals(HEADING_SPACING_AFTER, headingParagraph.getSpacingAfter());
        assertTrue(headingRun.isBold());
        assertEquals(HEADING_FONT_SIZE, headingRun.getFontSize());

        // Assert estimate description matches.
        assertParagraphOutput(
                paragraphs.subList(1, paragraphs.size()),
                Arrays.asList(estimateDescription, companyName, companyAddress, customerName, customerAddress),
                estimate
        );

        // Assert table exists.
        assertNotNull(tables);
        assertEquals(1, tables.size());

        // Assert estimate table content.
        XWPFTable estimateDocumentTable = tables.get(0);

    }

    private void assertParagraphOutput(List<XWPFParagraph> paragraphs, List<String> expectedParagraphText, Estimate estimate) {
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph paragraph = paragraphs.get(i);
            XWPFRun run = paragraph.getRuns().get(0);

            if (paragraph.getText().contains("Estimate ID: ")) {
                assertEquals("Estimate ID: " + estimate.getEstimateId() + "\nDate: " + estimate.getDate(), paragraph.getText());
            } else if (paragraph.getText().contains("[COMPANY NAME]")) {
                assertEquals("From: " + "\n" + estimate.getCompanyName() + "\n" + estimate.getCompanyAddress(),
                        paragraph.getText());
            } else if (paragraph.getText().contains("[CUSTOMER NAME]")) {
                assertEquals("To: " + "\n" + estimate.getCustomerName() + "\n" + estimate.getCustomerAddress(),
                        paragraph.getText());
            } else if (paragraph.getText().contains("Total Cost: $")) {
                String expectedTotalCostString = "\nTotal Cost: $" + estimate.getEstimateTable().getTotalCost().toPlainString();
                assertEquals(expectedTotalCostString, paragraph.getText());
            } else {
                assertEquals(expectedParagraphText.get(i), paragraph.getText());
                assertEquals(DEFAULT_SPACING_AFTER, paragraph.getSpacingAfter());
                assertEquals(PARAGRAPH_FONT_SIZE, run.getFontSize());
            }
        }
    }

}