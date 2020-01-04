package com.example.fuellog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.fuellog.MainActivity.EXTRA_DATA_ID;
import static com.example.fuellog.MainActivity.EXTRA_DATA_UPDATE_LOG;


public class NewLogActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.fuellog.REPLY";
    public static final String EXTRA_REPLY_ID = "com.example.android.fuellog.REPLY_ID";
    private LogViewModel logViewModel;
    private static final String TAG = "NewLogActivity";
    private Calculator mCalculator;
    private EditText mEditLogOdometer;
    private EditText mEditLogFuel;
    private EditText mEditLogPrice;
    private TextView mResultTextView;
    private EditText mReturnText;
    private View Update;
    private Integer Id;
    private String distance, price, amount;
    EditText logDistance, logPrice, logAmount;
    /**
     * @return the operand value entered in an EditText as double.
     */
    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    /**
     * @return the operand text which was entered in an EditText.
     */
    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
        logViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);
        mReturnText = findViewById(R.id.Output);


        // Initialize the calculator class and all the views
        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            Update = findViewById(R.id.update);
            Id = extras.getInt("id", 0);
            String dist = extras.getString("distance", "");
            String amo = extras.getString("fuelAmount", "");
            String pri = extras.getString("fuelPrice", "");

            if (!dist.isEmpty()) {
                mEditLogOdometer.setText(dist);
                mEditLogOdometer.setSelection(dist.length());
                mEditLogOdometer.requestFocus();

                mEditLogFuel.setText(amo);
                mEditLogFuel.setSelection(amo.length());
                mEditLogFuel.requestFocus();

                mEditLogPrice.setText(pri);
                mEditLogPrice.setSelection(pri.length());
                mEditLogPrice.requestFocus();
            }
        } // Otherwise, start with empty fields.

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity).

    }

    public void update(View view){

        Intent intent = new Intent();
        logDistance = findViewById(R.id.edit_log_odom);
        distance = logDistance.getText().toString();
        logAmount = findViewById(R.id.edit_log_fuel);
        amount = logAmount.getText().toString();
        logPrice = findViewById(R.id.edit_log_price);
        price = logPrice.getText().toString();
        intent.putExtra("id",Id);
        intent.putExtra("updateDist",distance );
        intent.putExtra("updateAmo",amount );
        intent.putExtra("updatePri",price );
        setResult(2,intent);
        finish();
    }

    public void saveData(View view) {
       try {
           Log.d(TAG, "saveData: method called");
           logDistance = findViewById(R.id.edit_log_odom);
           distance = logDistance.getText().toString();
           logAmount = findViewById(R.id.edit_log_fuel);
           amount = logAmount.getText().toString();
           logPrice = findViewById(R.id.edit_log_price);
           price = logPrice.getText().toString();
           Log.d(TAG, "saveData: distance" + distance + " " + amount + " " + price);
           LogCar log = new LogCar(distance, amount, price);
           logViewModel.insert(log);
           Intent intent = new Intent(this,MainActivity.class);
           startActivity(intent);
       }
       catch(Exception e){
           Log.e(TAG, "saveData: "+  e.getMessage());
       }
    }


    public void fetchData(View view) {
        new GetCar(mReturnText).execute("");
    }


    public void onMilesPerGallon(View view) {
        compute(Calculator.Operator.MilesPerGallon);
    }


    public void onKMPerLitre(View view) {
        compute(Calculator.Operator.KMPerLitre);
    }
    public void onTest(View view) {
        compute(Calculator.Operator.test);
    }


    public void onGallon(View view) {
        try {
            compute(Calculator.Operator.CostPerGallon);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException", iae);
            mResultTextView.setText(getString(R.string.computationError));
        }
    }

    /**
     * OnClick method called when the multiply Button is pressed.
     */
    public void onMile(View view) {
        compute(Calculator.Operator.CostPerMile);
    }

    private void compute(Calculator.Operator operator) {
        double operandOne;
        double operandTwo;
        double operandThree;
        double operandFour;
        try {
            operandOne = getOperand(mEditLogOdometer);
            operandTwo = getOperand(mEditLogFuel);
            operandThree = getOperand(mEditLogPrice);
            operandFour = getOperand((EditText) mReturnText);
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            mResultTextView.setText(getString(R.string.computationError));
            return;
        }

        String result;
        switch (operator) {
            case MilesPerGallon:
                result = String.valueOf(
                        mCalculator.MilesPerGallon(operandOne, operandTwo));
                break;
            case KMPerLitre:
                result = String.valueOf(
                        mCalculator.KMPerLitre(operandOne, operandTwo));
                break;
            case CostPerGallon:
                result = String.valueOf(
                        mCalculator.CostPerGallon(operandThree, operandOne));
                break;
            case CostPerMile:
                result = String.valueOf(
                        mCalculator.CostPerMile(operandThree, operandTwo));
                break;
            case test:
                result = String.valueOf(
                        mCalculator.test(operandFour));
                break;
            default:
                result = getString(R.string.computationError);
                break;
        }
        mResultTextView.setText(result);
    }


   }


