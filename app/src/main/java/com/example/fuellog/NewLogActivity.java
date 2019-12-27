package com.example.fuellog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class NewLogActivity extends AppCompatActivity {
    private static final String TAG = "NewLogActivity";
//    public static final String EXTRA_REPLY =
//            "com.example.android.roomwordssample.REPLY";

    public static final int EXTRA_REPLY = 1;
    private Calculator mCalculator;
    private EditText mEditLogOdometer;
    private EditText mEditLogFuel;
    private EditText mEditLogPrice;
    private TextView mResultTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);

        // Initialize the calculator class and all the views
        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mEditLogOdometer = findViewById(R.id.edit_log_odom);
        mEditLogFuel = findViewById(R.id.edit_log_fuel);
        mEditLogPrice = findViewById(R.id.edit_log_price);
    }

    /**
     * OnClick method called when the add Button is pressed.
     */
    public void onAdd(View view) {
        compute(Calculator.Operator.ADD);
    }

    /**
     * OnClick method called when the subtract Button is pressed.
     */
    public void onSub(View view) {
        compute(Calculator.Operator.SUB);
    }

    /**
     * OnClick method called when the divide Button is pressed.
     */
    public void onDiv(View view) {
        try {
            compute(Calculator.Operator.DIV);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException", iae);
            mResultTextView.setText(getString(R.string.computationError));
        }
    }

    /**
     * OnClick method called when the multiply Button is pressed.
     */
    public void onMul(View view) {
        compute(Calculator.Operator.MUL);
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
            case ADD:
                result = String.valueOf(
                        mCalculator.add(operandOne, operandTwo));
                break;
            case SUB:
                result = String.valueOf(
                        mCalculator.sub(operandOne, operandTwo));
                break;
            case DIV:
                result = String.valueOf(
                        mCalculator.div(operandOne, operandTwo));
                break;
            case MUL:
                result = String.valueOf(
                        mCalculator.mul(operandOne, operandTwo));
                break;
            default:
                result = getString(R.string.computationError);
                break;
        }
        mResultTextView.setText(result);
    }

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






    public void saveData(View view) {
        Log.d(TAG, "saveData: method called");
        try{
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
}
