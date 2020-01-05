package com.example.fuellog;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int EXTRA_REPLY = 1;
    public static final int EXTRA_UPDATE = 2;
    public static final String EXTRA_DATA_UPDATE_LOG = "extra_log_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    private static final String TAG = "MainActivity";
    private LogViewModel mLogViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewLogActivity.class);
                startActivityForResult(intent, EXTRA_REPLY);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final LogListAdapter adapter = new LogListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);
        mLogViewModel.getAllLogs().observe(this, new Observer<List<LogCar>>() {
            @Override
            public void onChanged(@Nullable final List<LogCar> logCars) {
                adapter.setLogs(logCars);
            }
        });



        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        LogCar myLogCar = adapter.getLogAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myLogCar.getLog(), Toast.LENGTH_LONG).show();

                        // Delete the log
                        mLogViewModel.deleteLog(myLogCar);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LogListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                LogCar logCar = adapter.getLogAtPosition(position);
                launchNewLogActivity(logCar);
            }
        });
    }



    private void launchNewLogActivity(LogCar logCar) {
        Intent intent = new Intent(this, NewLogActivity.class);
        Bundle extras = new Bundle();

        extras.putInt("id", logCar.getId());
        extras.putString("distance", logCar.getLog());
        extras.putString("fuelAmount", logCar.getLog3());
        extras.putString("fuelPrice", logCar.getLog2());
        intent.putExtras(extras);
        Log.e(TAG, "launchNewLogActivity: " + logCar.getId() );
        startActivityForResult(intent, EXTRA_UPDATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mLogViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
//    private LogViewModel mLogViewModel;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        android.util.Log.d(TAG, "onActivityResult: requestCode: " + requestCode + " resultCode: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EXTRA_REPLY) {
            android.util.Log.d(TAG, "onActivityResult: replyCode " + EXTRA_REPLY + " resultCode: " + RESULT_OK);
           // LogCar logCar = new LogCar(data.getStringExtra("updateDist"), data.getStringExtra("updateAmo"), data.getStringExtra("updatePri"));
          //  mLogViewModel.insert(logCar);
        }
        if (requestCode == EXTRA_UPDATE) {
            android.util.Log.d(TAG, "onActivityResult: replyCode " + EXTRA_REPLY + " resultCode: " + RESULT_OK);
            String log_data = data.getStringExtra("updateDist");
            String log2_data = data.getStringExtra("updateAmo");
            String log3_data = data.getStringExtra("updatePri");
            int id = data.getIntExtra("id", 0);
            mLogViewModel.update(new LogCar(id,log_data,log2_data,log3_data));

        }

    }
}