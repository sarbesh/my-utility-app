package com.sarbesh.alarmgps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sarbesh.alarmgps.utils.BankingUtils;

public class FixedDeposit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText principal;
    Spinner period;
    Spinner payoutPeriod;
    EditText interestRate;
    EditText investPeriod;

    BankingUtils bankingUtils = new BankingUtils();

    TextView interestValue;
    TextView maturityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd);

        Log.d("FD","adding payout array "+R.array.payout_period);

        Spinner payoutPeriodSpinner = findViewById(R.id.payout_spinner);
        ArrayAdapter<CharSequence> payAdapter = ArrayAdapter
                .createFromResource(this,R.array.payout_period,android.R.layout.simple_spinner_item);
        payAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payoutPeriodSpinner.setAdapter(payAdapter);
        payoutPeriodSpinner.setSelection(2);
        payoutPeriodSpinner.setOnItemSelectedListener(this);

        Log.d("FD","Added payout, adding period array "+R.array.lock_period);

        Spinner termPeriodSpinner = findViewById(R.id.period_spinner);
        ArrayAdapter<CharSequence> termAdaptor = ArrayAdapter
                .createFromResource(this,R.array.lock_period, android.R.layout.simple_spinner_item);
        termAdaptor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        termPeriodSpinner.setAdapter(termAdaptor);
        termPeriodSpinner.setSelection(2);
        termPeriodSpinner.setOnItemSelectedListener(this);

        Log.d("FD","Added lock period");

        principal = findViewById(R.id.editPrincipal);
        investPeriod = findViewById(R.id.editTimePeriod);
        interestRate = findViewById(R.id.editInterestRate);

        payoutPeriod = payoutPeriodSpinner;
        period = termPeriodSpinner;

        interestValue = findViewById(R.id.interest_value_view);
        maturityValue = findViewById(R.id.maturity_value_view);
    }

    public void calculateInterest(View view){
        long p = Long.parseLong(principal.getText().toString());
        double r = Double.parseDouble(interestRate.getText().toString());
        int t =Integer.parseInt(investPeriod.getText().toString());

        String tPeriod =  String.valueOf(period.getSelectedItem());
        int tp = 3;
        if(tPeriod.equals("Days")){
            tp=1;
        } else if(tPeriod.equals("Months")){
            tp=2;
        }

        String pPeriod = String.valueOf(payoutPeriod.getSelectedItem());
        int pP = 4;
        if(pPeriod.equals("Annually")){
            pP = 1;
        } else if(pPeriod.equals("Semiannual")){
            pP = 2;
        }

        long emi = bankingUtils.calculateFDInterest(p, t, r, pP, tp);
        maturityValue.setText(String.valueOf(emi));
        interestValue.setText(String.valueOf(emi-p));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}