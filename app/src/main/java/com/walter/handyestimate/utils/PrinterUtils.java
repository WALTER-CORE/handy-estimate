package com.walter.handyestimate.utils;

import android.bluetooth.BluetoothAdapter;

import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.brother.ptouch.sdk.PrinterStatus;

import java.util.logging.Logger;

public class PrinterUtils {

    private static final Logger logger = Logger.getLogger(PrinterUtils.class.getName());

    /**
     * Print the given file to the Brother Printer
     *
     * @param filePath file path to write to.
     * */
    public static void printFile(String filePath) {
        // Specify printer
        Printer printer = new Printer();
        PrinterInfo settings = printer.getPrinterInfo();
        settings.printerModel = PrinterInfo.Model.PJ_763MFi;
        settings.port = PrinterInfo.Port.NET;
        settings.ipAddress = "192.168.1.172";

        // Print Settings
        settings.numberOfCopies = 1;

        // For Bluetooth:
        printer.setBluetooth(BluetoothAdapter.getDefaultAdapter());
        settings.port = PrinterInfo.Port.BLUETOOTH;
        settings.macAddress = "your-printer-bluetooth-address";

        // For USB:
        settings.port = PrinterInfo.Port.USB;

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
}
