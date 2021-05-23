package com.walter.handyestimate.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.walter.handyestimate.R;
import com.walter.handyestimate.data.model.Estimate;
import com.walter.handyestimate.data.model.EstimateTable;


public class LoginActivity extends AppCompatActivity {

    private String companyName;
    private String companyAddress;
    private String customerName;
    private String customerAddress;
    private String estimateDescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Estimate estimate;
        EstimateTable estimateTable;

        final EditText companyNameEditText = findViewById(R.id.company_name);
        final EditText companyAddressEditText = findViewById(R.id.company_address);
        final EditText customerNameEditText = findViewById(R.id.customer_name);
        final EditText customerAddressEditText = findViewById(R.id.customer_address);
        final EditText estimateDescriptionEditText = findViewById(R.id.estimate_description);
        final Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyName = companyNameEditText.getText().toString();
                companyAddress = companyAddressEditText.getText().toString();
                customerName = customerNameEditText.getText().toString();
                customerAddress = customerAddressEditText.getText().toString();
                estimateDescription = estimateDescriptionEditText.getText().toString();

            }
        });
    }

//    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//    }
//
//    private void showLoginFailed(@StringRes Integer errorString) {
//        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
//    }
}