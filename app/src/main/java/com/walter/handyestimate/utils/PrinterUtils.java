package com.walter.handyestimate.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.brother.ptouch.sdk.NetPrinter;
import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.brother.ptouch.sdk.PrinterStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PrinterUtils {

    private static final Logger logger = Logger.getLogger(PrinterUtils.class.getName());

    /**
     * Print the given file to the Brother Printer
     *
     * @param filePath file path to write to.
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void printFile(String filePath) {
//        List<BluetoothDevice> devices = getPairedBluetoothDevice(BluetoothAdapter.getDefaultAdapter());
//        List<NetPrinter> netPrinters = getPairedPrinters();

        // Specify printer
        Printer printer = new Printer();
        PrinterInfo settings = printer.getPrinterInfo();
        settings.printerModel = PrinterInfo.Model.PJ_763MFi;
//        settings.port = PrinterInfo.Port.NET;
//        settings.ipAddress = "";

        // Print Settings
        settings.numberOfCopies = 1;

        // For Bluetooth:
        printer.setBluetooth(BluetoothAdapter.getDefaultAdapter());
        settings.port = PrinterInfo.Port.BLUETOOTH;
        settings.macAddress = "60:77:71:BE:C4:8F"; //netPrinters.get(0).macAddress;
         // // TODO: DON'T COMMIT THIS ADDRESS


//
//        // For USB:
//        settings.port = PrinterInfo.Port.USB;

        printer.setPrinterInfo(settings);

        // Connect, then print
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (printer.startCommunication()) {
                    PrinterStatus result = printer.printFile(filePath);
                    if (result.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                        logger.severe("Error when printing file: " + filePath
                                + "\nPRINTER ERROR CODE: " + result.errorCode);
                    }
                    printer.endCommunication();
                }
            }
        }).start();
    }

    public static void printPDF(String filePath, Context context) {
        Printer printer = new Printer();
        PrinterInfo settings = printer.getPrinterInfo();
        settings.printerModel = PrinterInfo.Model.PJ_763MFi;
        settings.paperSize = PrinterInfo.PaperSize.A4;
        settings.printMode = PrinterInfo.PrintMode.FIT_TO_PAPER;
        settings.pjPaperKind = PrinterInfo.PjPaperKind.PJ_CUT_PAPER;


//        settings.port = PrinterInfo.Port.NET;
//        settings.ipAddress = "";

        // Print Settings
        settings.numberOfCopies = 1;
        settings.labelNameIndex = PrinterInfo.Model.PJ_763MFi.getDefaultPaper();
        settings.workPath = context.getFilesDir().getAbsolutePath() + "/";

        // For Bluetooth:
        printer.setBluetooth(BluetoothAdapter.getDefaultAdapter());
        settings.port = PrinterInfo.Port.BLUETOOTH;
        settings.macAddress = "60:77:71:BE:C4:8F"; //netPrinters.get(0).macAddress;
        // // TODO: DON'T COMMIT THIS ADDRESS

//
//        // For USB:
//        settings.port = PrinterInfo.Port.USB;

        printer.setPrinterInfo(settings);

        // Connect, then print
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (printer.startCommunication()) {
                    PrinterStatus result = printer.printPdfFile(filePath, 1);
                    if (result.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                        logger.severe("Error when printing PDF"
                                + "\nPRINTER ERROR CODE: " + result.errorCode);
                    }
                    printer.endCommunication();
                }
            }
        }).start();
    }

    /**
     * Print the given bitmap image to the Brother Printer
     *
     * @param bitmap image bitmap to print.
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void printBitMap(Bitmap bitmap, String workPath) {
//        List<BluetoothDevice> devices = getPairedBluetoothDevice(BluetoothAdapter.getDefaultAdapter());
//        List<NetPrinter> netPrinters = getPairedPrinters();

        // Specify printer
        Printer printer = new Printer();
        PrinterInfo settings = printer.getPrinterInfo();
        settings.printerModel = PrinterInfo.Model.PJ_763MFi;
        settings.paperSize = PrinterInfo.PaperSize.A4;
        settings.orientation = PrinterInfo.Orientation.LANDSCAPE;
        settings.valign = PrinterInfo.VAlign.MIDDLE;
        settings.align = PrinterInfo.Align.CENTER;
        settings.printMode = PrinterInfo.PrintMode.ORIGINAL;
//        settings.port = PrinterInfo.Port.NET;
//        settings.ipAddress = "";

        // Print Settings
        settings.numberOfCopies = 1;
        settings.labelNameIndex = PrinterInfo.Model.PJ_763MFi.getDefaultPaper();
        settings.workPath = workPath;

        // For Bluetooth:
        printer.setBluetooth(BluetoothAdapter.getDefaultAdapter());
        settings.port = PrinterInfo.Port.BLUETOOTH;
        settings.macAddress = "60:77:71:BE:C4:8F"; //netPrinters.get(0).macAddress;
        // // TODO: DON'T COMMIT THIS ADDRESS


//
//        // For USB:
//        settings.port = PrinterInfo.Port.USB;

        printer.setPrinterInfo(settings);

        // Connect, then print
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (printer.startCommunication()) {
                    PrinterStatus result = printer.printImage(bitmap);
                    if (result.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                        logger.severe("Error when printing bitmap"
                                + "\nPRINTER ERROR CODE: " + result.errorCode);
                    }
                    printer.endCommunication();
                }
            }
        }).start();
    }

    /**
     * get paired printers
     */
    private static List<NetPrinter> getPairedPrinters() {
        List<NetPrinter> mBluetoothPrinter; // array of storing Printer information
        ArrayList<String> mItems = null; // List of storing the printer's information

        // get the BluetoothAdapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        try {
            if (mItems != null) {
                mItems.clear();
            }
            mItems = new ArrayList<String>();

            /*
             * if the paired devices exist, set the paired devices else set the
             * string of "No Bluetooth Printer."
             */
            List<BluetoothDevice> pairedDevices = getPairedBluetoothDevice(bluetoothAdapter);
            if (pairedDevices.size() > 0) {
                mBluetoothPrinter = new ArrayList<>(pairedDevices.size());
                int i = 0;
                for (BluetoothDevice device : pairedDevices) {
                    String strDev = "";
                    strDev += device.getName() + "\n" + device.getAddress();
                    mItems.add(strDev);

                    mBluetoothPrinter.set(i, new NetPrinter());
                    mBluetoothPrinter.get(i).ipAddress = "";
                    mBluetoothPrinter.get(i).macAddress = device.getAddress();
                    mBluetoothPrinter.get(i).modelName = device.getName();
                    i++;
                }
                return mBluetoothPrinter;

            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static List<BluetoothDevice> getPairedBluetoothDevice(BluetoothAdapter bluetoothAdapter) {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices == null || pairedDevices.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<BluetoothDevice> devices = new ArrayList<>();
        for (BluetoothDevice device : pairedDevices) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                devices.add(device);
            }
            else {
                if (device.getType() != BluetoothDevice.DEVICE_TYPE_LE) {
                    devices.add(device);
                }
            }
        }

        return devices;
    }

    /**
     * Given the list of lines and text settings it converts the strings into a Bitmap image
     * ready to print.
     * @param lines The lines of text to be converted to a Bitmap image.
     * @param textSize Font size for the text on the Bitmap image.
     * @param textColor Text color for the text on the Bitmap image.
     * */
    public static Bitmap textAsBitmap(List<String> lines, float textSize, int textColor) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent();
        // Hard coded to the first line item assuming the text is all the same.
        int width = (int) (paint.measureText(lines.get(0)) + 0.5f);
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width + 500, height + 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawRect(0, 0, width + 500, height + 500, paint);
        paint.setColor(textColor);
        int increment = 0;
        // For every line of input draw it on the canvas and increment the next line.
        int row = 1;
        for (int i = 0; i < lines.size(); i++) {
            String label = lines.get(i);
            canvas.drawText(row + ". " + label, 0, baseline + increment, paint);
            if (i % 2 == 1) {
                row++;
            }
            increment += 100;
        }
        return image;
    }
}
