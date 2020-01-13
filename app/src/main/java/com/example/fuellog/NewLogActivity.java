package com.example.fuellog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class NewLogActivity extends AppCompatActivity {

    private LogViewModel logViewModel;
    private static final String TAG = "NewLogActivity";
    private Calculator mCalculator;
    private EditText mEditLogOdometer;
    private EditText mEditLogFuel;
    private EditText mEditLogPrice;
    private TextView mResultTextView;
    private EditText mReturnMPG;
    private EditText mReturnFuel;
    private View Update;
    private Integer Id;
    private String distance, price, amount;
    private EditText logDistance, logPrice, logAmount;

    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
        logViewModel = ViewModelProviders.of(this).get(LogViewModel.class);


        mReturnMPG = findViewById(R.id.MPGOutput);
        mReturnFuel = findViewById(R.id.FuelOutput);
        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);
        fetchEmission (this);
        fetchMPG(this);
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

    public void fetchMPG(NewLogActivity view) {
        new GetData (mReturnMPG).execute("");
    }
    public void fetchEmission(NewLogActivity view) {
        new GetEmissions(mReturnFuel).execute("");
    }
    public void onMilesPerGallon(View view) {
        compute(Calculator.Operator.MilesPerGallon);
    }
    public void onKMPerLitre(View view) {
        compute(Calculator.Operator.KMPerLitre);
    }
    public void onAPIKPL(View view) {
        compute(Calculator.Operator.APIKPL);
    }
    public void onCostPerLitre(View view) {
        compute(Calculator.Operator.CostPerLitre);
    }
    public void onCostPerKM(View view) {
        compute(Calculator.Operator.CostPerKM);
    }
    public void onMilesPerLitre(View view) {
        compute(Calculator.Operator.MilesPerLitre);
    }
    public void onKMPerGallon(View view) {
        compute(Calculator.Operator.KMPerGallon);
    }
    public void onKM(View view) {
        compute(Calculator.Operator.KM);
    }
    public void onLitre(View view) {
        compute(Calculator.Operator.Litre);
    }
    public void onGallon(View view) {
        try {
            compute(Calculator.Operator.CostPerGallon);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException", iae);
            mResultTextView.setText(getString(R.string.computationError));
        }
    }


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
            operandFour = getOperand((EditText) mReturnMPG);
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            mResultTextView.setText(getString(R.string.computationError));
            return;
        }

        String  result;
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
                        mCalculator.CostPerGallon(operandThree, operandTwo));
                break;
            case CostPerMile:
                result = String.valueOf(
                        mCalculator.CostPerMile(operandThree, operandOne));
                break;
            case APIKPL:
                result = String.valueOf(
                        mCalculator.APIKPL(operandFour));
                break;
            case CostPerLitre:
                result = String.valueOf(
                        mCalculator.CostPerLitre(operandThree, operandTwo));
                break;
            case CostPerKM:
                result = String.valueOf(
                        mCalculator.CostPerKM(operandThree, operandOne));
                break;
            case MilesPerLitre:
                result = String.valueOf(
                        mCalculator.MilesPerLitre(operandOne, operandTwo));
                break;
            case KMPerGallon:
                result = String.valueOf(
                        mCalculator.KMPerGallon(operandOne,operandTwo));
                break;
            case KM:
                result = String.valueOf(
                        mCalculator.KM(operandOne));
                break;
            case Litre:
                result = String.valueOf(
                        mCalculator.Litre(operandTwo));
                break;
            default:
                result = getString(R.string.computationError);
                break;
        }
        mResultTextView.setText(result);

    }
   }


