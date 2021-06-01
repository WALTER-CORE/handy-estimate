package com.walter.handyestimate.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
                    PrinterStatus result = printer.printPdfFile(filePath, 1);
                    if (result.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                        logger.severe("Error when printing file: " + filePath
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
}
