package com.walter.handyestimate.utils;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateLineItem;
import com.walter.handyestimate.data.model.EstimateTable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;


public class PDFFileUtilsTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testWriteEstimateToPDF() throws IOException {

        String path = folder.newFile("estimatePDF.pdf").getAbsolutePath();
        File pdf = new File(path);

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

        assertNotNull(pdf);
    }

    @Test
    public void testTableToPDF() throws IOException {
        String path = folder.newFile("estimateTablePDF.pdf").getAbsolutePath();
        File pdf = new File(path);

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

        assertNotNull(pdf);
    }

}