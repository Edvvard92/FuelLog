package com.example.fuellog;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int EXTRA_REPLY = 1;
    public static final int EXTRA_UPDATE = 2;
    private static final String TAG = "MainActivity";
    private LogViewModel mLogViewModel;
    private TextView mReturnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReturnText = (TextView) findViewById(R.id.Output);

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
        mLogViewModel.getAllLogs().observe(this, new Observer<List<Log>>() {
            @Override
            public void onChanged(@Nullable final List<Log> logs) {
                adapter.setLogs(logs);
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
                        Log myLog = adapter.getLogAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myLog.getLog(), Toast.LENGTH_LONG).show();

                        // Delete the log
                        mLogViewModel.deleteLog(myLog);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LogListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log log = adapter.getLogAtPosition(position);
                launchNewLogActivity(log);
            }
        });
    }

    public void fetchData(View view) {
        new GetCar(mReturnText).execute("");
    }

    private void launchNewLogActivity(Log log) {
        Intent intent = new Intent(this, NewLogActivity.class);
        Bundle extras = new Bundle();

        extras.putInt("id", log.getId());
        extras.putString("distance", log.getLog());
        extras.putString("fuelAmount", log.getLog3());
        extras.putString("fuelPrice", log.getLog2());
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
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

        android.util.Log.d(TAG, "onActivityResult: value1:" + data.getStringExtra("value1"));
        android.util.Log.d(TAG, "onActivityResult: value2:" + data.getStringExtra("value2"));
        android.util.Log.d(TAG, "onActivityResult: value3:" + data.getStringExtra("value3"));
        if (requestCode == EXTRA_REPLY) {
            android.util.Log.d(TAG, "onActivityResult: replyCode " + EXTRA_REPLY + " resultCode: " + RESULT_OK);
            Log log = new Log(data.getStringExtra("value1"), data.getStringExtra("value2"), data.getStringExtra("value3"));
            mLogViewModel.insert(log);
        } else if (requestCode == EXTRA_UPDATE) {
            android.util.Log.d(TAG, "onActivityResult: replyCode " + EXTRA_REPLY + " resultCode: " + RESULT_OK);
            String log_data = data.getStringExtra("value1");
            String log2_data = data.getStringExtra("Value2");
            String log3_data = data.getStringExtra("Value3");
            int id = data.getIntExtra("id", 1);

            if (id != -1) {
                mLogViewModel.update(new Log(id, log_data, log2_data, log3_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
