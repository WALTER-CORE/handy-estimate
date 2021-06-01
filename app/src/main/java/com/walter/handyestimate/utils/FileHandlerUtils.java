package com.walter.handyestimate.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateLineItem;
import com.walter.handyestimate.data.model.EstimateTable;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandlerUtils {

    private static Logger logger = Logger.getLogger(FileHandlerUtils.class.getName());
    public static final int HEADING_FONT_SIZE = 20;
    public static final int HEADING_SPACING_AFTER = 500;
    public static final int PARAGRAPH_FONT_SIZE = 12;
    public static final int DEFAULT_SPACING_AFTER = 1000;

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
            writeEstimateTable(document, estimate.getEstimateTable());
            // Write the total cost under teh table.
            XWPFParagraph totalParagraph = getSpacedParagraph(document, DEFAULT_SPACING_AFTER);
            totalParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun totalRun = getParagraphRun(totalParagraph);
            totalRun.addBreak();
            totalRun.setBold(true);
            totalRun.setText("Total Cost: $" + estimate.getEstimateTable().getTotalCost().toPlainString());

            // TODO: Add default section spacing.

            // Write content set using XWPF classes available
            document.write(outputStream);
            outputStream.close();
            document.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception when writing to the filepath: " + filePath, e);
        }
    }

//    public static void convertDocxToPdf(String filePath, String tempFile) throws Exception {
//        InputStream in = new FileInputStream(filePath);
//        XWPFDocument document = new XWPFDocument(in);
//        // there must be a styles document, even if it is empty
//        XWPFStyles styles = document.getStyles();
//        // there must be section properties for the page having at least the page size set
//        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
//        CTPageSz pageSz = sectPr.addNewPgSz();
//        pageSz.setW(BigInteger.valueOf(12240)); //12240 Twips = 12240/20 = 612 pt = 612/72 = 8.5"
//        pageSz.setH(BigInteger.valueOf(15840)); //15840 Twips = 15840/20 = 792 pt = 792/72 = 11"
//
//        //document must be written so underlaaying objects will be committed
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        document.write(out);
//        out.close();
//        document.close();
//
//        PdfOptions options = PdfOptions.create();
//        OutputStream pdfOut = new FileOutputStream(new File(filePath.replace(".docx", ".pdf")));
//        PdfConverter.getInstance().convert(document, pdfOut, options);
//    }

    public static Bitmap convertFileToBitMap(String filePath) {
        return BitmapFactory.decodeFile(filePath);
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
        XWPFRun miscDetailsRun = getParagraphRun(document);
        miscDetailsRun.setText("Estimate ID: " + estimateId);
        miscDetailsRun.addBreak();
        miscDetailsRun.setText("Date: " + date.toString());
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
    private static XWPFRun getParagraphRun(XWPFParagraph paragraph) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(PARAGRAPH_FONT_SIZE);

        return run;
    }

    private static XWPFParagraph getSpacedParagraph(XWPFDocument document, int spacing) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(spacing);

        return paragraph;
    }

    private static void writeEstimateTable(XWPFDocument document, EstimateTable estimateTable) {
        XWPFTable table = document.createTable();
        table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(2*1440));
        //other columns (2 in this case) also each 2 inches width
        for (int col = 1 ; col < 3; col++) {
            table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(2*1440));
        }
        table.setWidth("100%");

        //Creating first Row with labels
        XWPFTableRow labelsRow = table.getRow(0);
        setTableCell(labelsRow.getCell(0), "Description", true, false);
        setTableCell(labelsRow.addNewTableCell(), "Quantity", true, false);
        setTableCell(labelsRow.addNewTableCell(), "Rate", true, false);
        setTableCell(labelsRow.addNewTableCell(), "Cost", true, false);

        List<EstimateLineItem> estimateLineItemList = estimateTable.getEstimateLineItemList();
        for (EstimateLineItem estimateLineItem : estimateLineItemList) {
            XWPFTableRow row = table.createRow();
            setTableCell(row.getCell(0), estimateLineItem.getDescription(), false, false);
            setTableCell(row.getCell(1), String.valueOf(estimateLineItem.getQuantity()), false, false);
            setTableCell(row.getCell(2), "$" + getFormattedBigDecimalCost(estimateLineItem.getRate()), false, false);
            setTableCell(row.getCell(3), "$" + getFormattedBigDecimalCost(estimateLineItem.getCost()), false, false);
        }
    }

    private static void setTableCell(XWPFTableCell cell, String text , boolean bold , boolean addBreak) {
        // Remove initial pargraph in the cell.
        cell.removeParagraph(0);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

        // Add new paragraph with correct text.
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(bold);
        if (addBreak) run.addBreak();
    }

    private static String getFormattedBigDecimalCost(BigDecimal cost) {
        cost = cost.setScale(2, RoundingMode.DOWN);

        DecimalFormat df = new DecimalFormat("0.00");

        df.setMaximumFractionDigits(2);

        df.setMinimumFractionDigits(0);

        df.setGroupingUsed(false);

        return df.format(cost);
    }
}
