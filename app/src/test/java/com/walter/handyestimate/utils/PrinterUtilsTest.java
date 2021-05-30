package com.walter.handyestimate.utils;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateLineItem;
import com.walter.handyestimate.data.model.EstimateTable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static com.walter.handyestimate.utils.FileHandlerUtils.writeEstimateToFile;
import static com.walter.handyestimate.utils.PrinterUtils.printFile;

public class PrinterUtilsTest {

    /* This folder and the files created in it will be deleted after
     * tests are run, even in the event of failures or exceptions.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

//    @Ignore("For manual testing purposes only")
    @Test
    public void testPrintFile() throws IOException {
        String path = folder.newFile("printTestFile").getAbsolutePath();
        String estimateDescription = "HELLO I'M THE TEST FILE FOR TEST: testPrintFile()";
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
        printFile(path);
    }
}