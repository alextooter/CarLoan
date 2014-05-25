package com.OldYellowRiver.carloan.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    // Content View Elements

    private TextView mTextCarPrice;
    private EditText mEditTextCarPrice;
    private EditText mEditTextCarRealPrice;
    private TextView mTextCarType;
    private Spinner  mSpinnerCarType;
    private TextView mTextDownPercent;
    private Spinner  mSpinnerDownPercent;
    private TextView mTextDownPayment;
    private EditText mEditDownPayment;
    private TextView mTextLoanYear;
    private EditText mEditLoanYear;
    private TextView mTextLoan;
    private EditText mEditLoan;
    private TextView mTextBuyTax;
    private EditText mEditBuyTax;
    private TextView mTextInsurance;
    private EditText mEditInsurance;
    private TextView mTextPDI;
    private EditText mEditPDI;
    private TextView mTextMangerFee;
    private EditText mEditManagerFee;
    private TextView mTextTotalFirstPay;
    private EditText mEditTotalFirstPay;
    private TextView mTextMonthPay;
    private EditText mEditMonthPay;
    private Button mButtonClear;
    private Button mButtonCalc;

    // End Of Content View Elements

    DataObject object = new DataObject();

    private void bindViews() {

        mTextCarPrice = (TextView) findViewById(R.id.textCarPrice);
        mEditTextCarPrice = (EditText) findViewById(R.id.editTextCarPrice);
        mEditTextCarRealPrice = (EditText) findViewById(R.id.editTextCarRealPrice);

        mTextCarType = (TextView) findViewById(R.id.textCarType);
        mSpinnerCarType = (Spinner) findViewById(R.id.spinnerCarType);

        mTextDownPercent = (TextView) findViewById(R.id.textDownPercent);
        mSpinnerDownPercent = (Spinner) findViewById(R.id.spinnerDownPercent);

        mTextDownPayment = (TextView) findViewById(R.id.textDownPayment);
        mEditDownPayment = (EditText) findViewById(R.id.editDownPayment);
        mTextLoanYear = (TextView) findViewById(R.id.textLoanYear);
        mEditLoanYear = (EditText) findViewById(R.id.editLoanYear);
        mTextLoan = (TextView) findViewById(R.id.textLoan);
        mEditLoan = (EditText) findViewById(R.id.editLoan);
        mTextBuyTax = (TextView) findViewById(R.id.textBuyTax);
        mEditBuyTax = (EditText) findViewById(R.id.editBuyTax);
        mTextInsurance = (TextView) findViewById(R.id.textInsurance);
        mEditInsurance = (EditText) findViewById(R.id.editInsurance);
        mTextPDI = (TextView) findViewById(R.id.textPDI);
        mEditPDI = (EditText) findViewById(R.id.editPDI);
        mTextMangerFee = (TextView) findViewById(R.id.textMangerFee);
        mEditManagerFee = (EditText) findViewById(R.id.editManagerFee);
        mTextTotalFirstPay = (TextView) findViewById(R.id.textTotalFirstPay);
        mEditTotalFirstPay = (EditText) findViewById(R.id.editTotalFirstPay);
        mTextMonthPay = (TextView) findViewById(R.id.textMonthPay);
        mEditMonthPay = (EditText) findViewById(R.id.editMonthPay);
        mButtonClear = (Button) findViewById(R.id.buttonClear);
        mButtonCalc = (Button) findViewById(R.id.buttonCalc);
    }

    // clear all EditText in a viewGroup.
    private void clearForm (ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i ++ ) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            // if it is a ViewGroup, then clear all its child.
            if (view instanceof ViewGroup &&
                    ((ViewGroup) view).getChildCount() > 0) {
                clearForm((ViewGroup)view);
            }
        }
    }

    String tmp;
    private ArrayAdapter<CharSequence> carTypeArrayAdapter;
    private ArrayAdapter<CharSequence> downPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        carTypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.car_types, android.R.layout.simple_spinner_item);
        carTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCarType.setAdapter(carTypeArrayAdapter);
        mSpinnerCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmp = adapterView.getItemAtPosition(i).toString();

                if (tmp.equals("大车"))
                   object.car_type = 200;
                else if (tmp.equals("小车"))
                   object.car_type = 150;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        downPercent = ArrayAdapter.createFromResource(this, R.array.percents, android.R.layout.simple_spinner_item);
        downPercent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDownPercent.setAdapter(downPercent);
        mSpinnerDownPercent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmp = adapterView.getItemAtPosition(i).toString();
                object.down_percent = Integer.parseInt(tmp.trim());
                tmp = mEditTextCarRealPrice.getText().toString().trim();
                if (tmp.isEmpty() == false) {
                    object.car_real_price = Double.parseDouble(tmp);
                }

                object.down_payment = object.down_percent * object.car_real_price /100;

                mEditDownPayment.setText(object.down_payment.toString());
                object.loan = object.car_real_price - object.down_payment;
                mEditLoan.setText(object.loan.toString());

                tmp = mEditTextCarPrice.getText().toString().trim();
                if (tmp.isEmpty() == false) {
                    object.car_price = Double.parseDouble(tmp);
                }
                object.buy_tax = ((Double)object.car_price) / 11.7;
                mEditBuyTax.setText(object.buy_tax.toString());

                tmp = mEditLoanYear.getText().toString().trim();
                if (tmp.isEmpty() == false) {
                    object.loan_year = Integer.parseInt(tmp);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        mButtonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "OK",Toast.LENGTH_SHORT).show();
                tmp = mEditBuyTax.getText().toString().trim();
                if (tmp.isEmpty() == false) object.car_price = Double.parseDouble(tmp);

                tmp = mEditDownPayment.getText().toString().trim();
                if (tmp.isEmpty() == false) object.down_payment = Double.parseDouble(tmp);
                //tmp = mEditDownPercent.getText().toString().trim();
                //if (tmp.isEmpty() == false) object.down_percent = Integer.parseInt(tmp);
                tmp = mEditInsurance.getText().toString().trim();
                if (tmp.isEmpty() == false) object.insurance = Integer.parseInt(tmp);
                tmp = mEditLoan.getText().toString().trim();
                if (tmp.isEmpty() == false) object.loan = Double.parseDouble(tmp);
                tmp = mEditLoanYear.getText().toString().trim();
                if (tmp.isEmpty() == false) object.loan_year = Integer.parseInt(tmp);
                tmp = mEditManagerFee.getText().toString().trim();
                if (tmp.isEmpty() == false) object.manager_fee = Integer.parseInt(tmp);
                tmp = mEditMonthPay.getText().toString().trim();
                if (tmp.isEmpty() == false) object.month_pay = Double.parseDouble(tmp);
                tmp = mEditPDI.getText().toString().trim();
                if (tmp.isEmpty() == false) object.PDI = Integer.parseInt(tmp);


                tmp = mEditTextCarPrice.getText().toString().trim();
                if (tmp.isEmpty() == false) object.car_price = Double.parseDouble(tmp);
                tmp = mEditTextCarRealPrice.getText().toString().trim();
                if (tmp.isEmpty() == false) object.car_real_price = Double.parseDouble(tmp);
                tmp = mEditTotalFirstPay.getText().toString().trim();
                if (tmp.isEmpty() == false) object.total_first_pay = Double.parseDouble(tmp);

                object.calcAll();

                object.manager_fee = object.car_type * object.loan_year;
                mEditManagerFee.setText(object.manager_fee.toString());
                mEditTotalFirstPay.setText(object.total_first_pay.toString());
                mEditMonthPay.setText(object.month_pay.toString());
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup viewGroup = (ViewGroup)findViewById(R.id.bigLayout);
                clearForm(viewGroup);
            }
       });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
