package com.walter.handyestimate.utils;

import com.walter.handyestimate.data.model.Estimate;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static com.walter.handyestimate.utils.FileHandlerUtils.writeEstimateToFile;
import static com.walter.handyestimate.utils.PrinterUtils.printFile;

public class PrinterUtilsTest {

    /* This folder and the files created in it will be deleted after
     * tests are run, even in the event of failures or exceptions.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Ignore("For manual testing purposes only")
    @Test
    public void testPrintFile() throws IOException {
        String path = folder.newFile("printTestFile").getAbsolutePath();
        String estimateDescription = "HELLO I'M THE TEST FILE FOR TEST: testPrintFile()";
        Estimate estimate = new Estimate(estimateDescription, null, null, null, null, null);

        writeEstimateToFile(estimate, path);
        printFile(path);
    }
}