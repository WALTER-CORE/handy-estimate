package com.walter.handyestimate.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.walter.handyestimate.R;
import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateLineItem;
import com.walter.handyestimate.data.model.EstimateTable;
import com.walter.handyestimate.utils.FileHandlerUtils;
import com.walter.handyestimate.utils.PrinterUtils;



import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private int lineQuantity; //if lineQuality == 0, don't call new constructor

    private String companyName;
    private String companyAddress;
    private String customerName;
    private String customerAddress;
    private String estimateDescription;
    private String item;
    private String itemDescription;

    private File estimateFile;
    private FileHandlerUtils fileHandler;

    private Estimate estimate;
    private EstimateTable estimateTable;
    private List<EstimateLineItem> lineItemList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lineItemList = new ArrayList<>();

        final EditText companyNameEditText = findViewById(R.id.company_name);
        final EditText companyAddressEditText = findViewById(R.id.company_address);
        final EditText customerNameEditText = findViewById(R.id.customer_name);
        final EditText customerAddressEditText = findViewById(R.id.customer_address);
        final EditText estimateDescriptionEditText = findViewById(R.id.estimate_description);
        final EditText itemEditText = findViewById(R.id.item);
        final EditText itemDescriptionEditText = findViewById(R.id.item_description);

        final Button submitButton = findViewById(R.id.submit);
        final Button plusQuantityButton = findViewById(R.id.plus_quantity);
        final Button minusQuantityButton = findViewById(R.id.minus_quantity);
        final Button addItemButton = findViewById(R.id.add_item);
        TextView quantity = findViewById(R.id.quantity);

        plusQuantityButton.setOnClickListener(view -> {
            lineQuantity++;
            quantity.setText("" + lineQuantity);
        });

        minusQuantityButton.setOnClickListener(view -> {
            if (lineQuantity > 0) {
                lineQuantity--;
                quantity.setText("" + lineQuantity);
            } else {
                System.out.println("Quantity must be greater than 0");
            }
        });

        addItemButton.setOnClickListener(view -> {
            item = itemEditText.getText().toString();
            itemDescription = itemDescriptionEditText.getText().toString();
            lineItemList.add(new EstimateLineItem(item, lineQuantity, BigDecimal.ONE));
        });

        submitButton.setOnClickListener(view -> {
            companyName = companyNameEditText.getText().toString();
            companyAddress = companyAddressEditText.getText().toString();
            customerName = customerNameEditText.getText().toString();
            customerAddress = customerAddressEditText.getText().toString();
            estimateDescription = estimateDescriptionEditText.getText().toString();

            //TODO: Construct new EstimateTable and use strings received to create the Estimate item.
            estimate = new Estimate(estimateDescription, companyName, companyAddress, customerName, customerAddress, new EstimateTable(lineItemList));
            try {
                print(estimate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void print(Estimate estimate) throws Exception {
        estimateFile = File.createTempFile("estimateFile", ".jpg", this.getCacheDir());
        BufferedImage image = IMAGEIO.read(estimateFile);
//        FileHandlerUtils.writeEstimateToFile(estimate, estimateFile.getAbsolutePath());
//        PrinterUtils.printBitMap(FileHandlerUtils.convertFileToBitMap(estimateFile.getAbsolutePath()));
//        estimateFile.deleteOnExit();
//        FileWriter myWriter = new FileWriter(this.getCacheDir() + "filename");
//        myWriter.write("Files in Java might be tricky, but it is fun enough!");
//        myWriter.close();
//        PrinterUtils.printFile(this.getCacheDir() + "filename");
        Bitmap myBM = BitmapFactory.decodeFile(estimateFile.getAbsolutePath());

    }
}