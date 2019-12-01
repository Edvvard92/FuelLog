package com.example.fuellog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;



public class NewLogActivity extends AppCompatActivity {
    private static final String TAG = "NewLogActivity";
//    public static final String EXTRA_REPLY =
//            "com.example.android.roomwordssample.REPLY";

    public static final int EXTRA_REPLY = 1;

    private EditText mEditLogView;
    private EditText mEditLogView1;
    private EditText mEditLogView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
        mEditLogView = findViewById(R.id.edit_log_odom);
        mEditLogView1 = findViewById(R.id.edit_log_fuel);
        mEditLogView2 = findViewById(R.id.edit_log_price);


//        final Button button = findViewById(R.id.button_save);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//            }
//        });
    }

    public void saveData(View view) {
        Log.d(TAG, "saveData: method called");
        try{
            Intent replyIntent1 = new Intent(NewLogActivity.this, MainActivity.class);

            Log.d(TAG, "saveData: value1 " + mEditLogView.getText().toString());
            Log.d(TAG, "saveData: value2 " + mEditLogView1.getText().toString());
            Log.d(TAG, "saveData: value3 " + mEditLogView2.getText().toString());
            replyIntent1.putExtra("value1", mEditLogView.getText().toString());
            replyIntent1.putExtra("value2", mEditLogView1.getText().toString());
            replyIntent1.putExtra("value3", mEditLogView2.getText().toString());
            setResult(EXTRA_REPLY, replyIntent1);
//            startActivity(replyIntent1);
//            startActivityForResult(replyIntent1,EXTRA_REPLY);
            finish();
        } catch (Exception e) {
            Log.d(TAG, "saveData: Error " + e.getMessage());
        }

    }
}
