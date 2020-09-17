package com.ashishlakhmani.homeautomation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch toggle, toggle_buzzer;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = (Switch) findViewById(R.id.toggle);
        toggle_buzzer = (Switch) findViewById(R.id.toggle_buzzer);
        image = (ImageView) findViewById(R.id.image);

        SharedPreferences notificationSP = getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        String fcm_token = notificationSP.getString(getString(R.string.FCM_TOKEN), "");

        InsertToDatabase insertToDatabase = new InsertToDatabase(this);
        insertToDatabase.execute(fcm_token);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleBackground toggleBackground = new ToggleBackground(MainActivity.this, image, "led");
                if (toggle.isChecked()) {
                    toggleBackground.execute("on");
                } else {
                    toggleBackground.execute("off");
                }
            }
        });


        toggle_buzzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleBackground toggleBackground = new ToggleBackground(MainActivity.this, "buzzer");

                if (toggle_buzzer.isChecked())
                    toggleBackground.execute("on");
                else
                    toggleBackground.execute("off");
            }
        });

    }


    public void onTemperature(View view) {
        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
    }
}
