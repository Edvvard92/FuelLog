package com.example.fuellog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class NewLogActivity extends AppCompatActivity {
    public static final int EXTRA_REPLY = 1;
    //    public static final String EXTRA_REPLY =
//            "com.example.android.roomwordssample.REPLY";
    private static final String TAG = "NewLogActivity";
    private Calculator mCalculator;
    private EditText mEditLogOdometer;
    private EditText mEditLogFuel;
    private EditText mEditLogPrice;
    private TextView mResultTextView;
    private TextView mReturnText;

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
        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);
        mReturnText = (TextView)findViewById(R.id.Output);
        // Initialize the calculator class and all the views
        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);




    }

    public void fetchData(View view) {
        new GetCar(mReturnText).execute("");
    }

    /**
     * OnClick method called when the add Button is pressed.
     */
    public void onMilesPerGallon(View view) {
        compute(Calculator.Operator.MilesPerGallon);
    }

    /**
     * OnClick method called when the subtract Button is pressed.
     */
    public void onKMPerLitre(View view) {
        compute(Calculator.Operator.KMPerLitre);
    }

    /**
     * OnClick method called when the divide Button is pressed.
     */
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
        try {
            operandOne = getOperand(mEditLogOdometer);
            operandTwo = getOperand(mEditLogFuel);
            operandThree = getOperand(mEditLogPrice);
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
            default:
                result = getString(R.string.computationError);
                break;
        }
        mResultTextView.setText(result);
    }

    public void saveData(View view) {
        Log.d(TAG, "saveData: method called");
        try {
            Intent replyIntent1 = new Intent(NewLogActivity.this, MainActivity.class);

            Log.d(TAG, "saveData: value1 " + mEditLogOdometer.getText().toString());
            Log.d(TAG, "saveData: value2 " + mEditLogFuel.getText().toString());
            Log.d(TAG, "saveData: value3 " + mEditLogPrice.getText().toString());
            replyIntent1.putExtra("value1", mEditLogOdometer.getText().toString());
            replyIntent1.putExtra("value2", mEditLogFuel.getText().toString());
            replyIntent1.putExtra("value3", mEditLogPrice.getText().toString());
            setResult(EXTRA_REPLY, replyIntent1);
//            startActivity(replyIntent1);
//            startActivityForResult(replyIntent1,EXTRA_REPLY);
            finish();
        } catch (Exception e) {
            Log.d(TAG, "saveData: Error " + e.getMessage());
        }
    }

    public void cancel(View view) {

        try {
            Intent replyIntent1 = new Intent(NewLogActivity.this, MainActivity.class);

            setResult(EXTRA_REPLY, replyIntent1);
            finish();
        } catch (Exception e) {
        }
    }
}
