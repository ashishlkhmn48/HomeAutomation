package com.ashishlakhmani.homeautomation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class TemperatureActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private BroadcastReceiver broadcastReceiver;
    private TemperatureAdapter adapter;

    private List<TemperatureDetails> temperatureDetailsList = new ArrayList<>();
    public static TemperatureDatabase temperatureDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new TemperatureAdapter(this, temperatureDetailsList);
        temperatureDatabase = new TemperatureDatabase(this, null, null, 1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        updateUI();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateUI();
            }
        };

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("UPDATE_UI"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.temperature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear");
            builder.setMessage("Do you really want to Clear ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    temperatureDatabase.deleteData();
                    temperatureDetailsList.clear();
                    adapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        temperatureDetailsList.clear();
        temperatureDetailsList.addAll(temperatureDatabase.getTemperatureDetails());
        if (!temperatureDetailsList.isEmpty()) {
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(temperatureDetailsList.size() - 1);
        }
    }

}
