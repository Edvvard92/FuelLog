package com.example.fuellog;

import androidx.appcompat.app.AppCompatActivity;

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
        int id = -1 ;
        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String log = extras.getString(EXTRA_DATA_UPDATE_LOG, "");
            if (!log.isEmpty()) {
                mEditLogFuel.setText(log);
                mEditLogFuel.setSelection(log.length());
                mEditLogFuel.requestFocus();

                mEditLogPrice.setText(log);
                mEditLogPrice.setSelection(log.length());
                mEditLogPrice.requestFocus();

                mEditLogOdometer.setText(log);
                mEditLogOdometer.setSelection(log.length());
                mEditLogOdometer.requestFocus();
            }
        } // Otherwise, start with empty fields.
        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity).
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditLogFuel.getText()) && TextUtils.isEmpty(mEditLogPrice.getText()) && TextUtils.isEmpty(mEditLogOdometer.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String log = mEditLogFuel.getText().toString();
                    String log = mEditLogPrice.getText().toString();
                    String log = mEditLogOdometer.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, log);
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
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

    /*public void saveData(View view) {
        Log.d(TAG, "saveData: method called");
        try {
            Intent replyIntent1 = new Intent(NewLogActivity.this, MainActivity.class);

            Log.d(TAG, "saveData: value1 " + mEditLogOdometer.getText().toString());
            Log.d(TAG, "saveData: value2 " + mEditLogFuel.getText().toString());
            Log.d(TAG, "saveData: value3 " + mEditLogPrice.getText().toString());
            replyIntent1.putExtra("value1", mEditLogOdometer.getText().toString());
            replyIntent1.putExtra("value2", mEditLogFuel.getText().toString());
            replyIntent1.putExtra("value3", mEditLogPrice.getText().toString());
            //setResult(EXTRA_REPLY, replyIntent1);
            startActivity(replyIntent1);
            startActivityForResult(replyIntent1,EXTRA_REPLY);
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
    }*/
}
