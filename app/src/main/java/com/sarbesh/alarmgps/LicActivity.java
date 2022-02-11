package com.sarbesh.alarmgps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sarbesh.alarmgps.utils.LicUtils;

public class LicActivity extends AppCompatActivity {

    LicUtils licUtils = new LicUtils();

    private TextView showInvestment;
    private TextView fdsReturn;

    EditText editTerm;
    EditText editPayPeriod;
    EditText editFirstPay;
    EditText editSecondPay;
    EditText editBankInterest;
    EditText editBank5Interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lic);
        editTerm = findViewById(R.id.editTerm);
        editPayPeriod = findViewById(R.id.editPayPeriod);
        editFirstPay = findViewById(R.id.editFirstPay);
        editSecondPay = findViewById(R.id.editSecondPay);
        editBankInterest = findViewById(R.id.editBankAnnualInterest);
        editBank5Interest = findViewById(R.id.editBank5Interest);

        showInvestment=findViewById(R.id.showInvestment);
        fdsReturn=findViewById(R.id.showFdsReturn);
    }

    public void calculate(View view){
        int term = Integer.parseInt(editTerm.getText().toString());
        int payPeriod = Integer.parseInt(editPayPeriod.getText().toString());
        int first = Integer.parseInt(editFirstPay.getText().toString());
        int second = Integer.parseInt(editSecondPay.getText().toString());
        double bankRate = Double.parseDouble(editBankInterest.getText().toString());
        double bank5 = Double.parseDouble(editBank5Interest.getText().toString());

        showInvestment.setText(String.valueOf(licUtils.investment(payPeriod,first,second)));
        fdsReturn.setText(String.valueOf(licUtils.getInvestment(term,payPeriod,first,second,bankRate,bank5)));
    }
}