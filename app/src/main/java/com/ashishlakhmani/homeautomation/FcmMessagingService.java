package com.ashishlakhmani.homeautomation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;


public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String temperature = remoteMessage.getData().get("temperature");
        String humidity = remoteMessage.getData().get("humidity");
        String date_time = remoteMessage.getData().get("date_time");

        TemperatureDatabase temperatureDatabase = new TemperatureDatabase(this, null, null, 1);
        TemperatureDetails temperatureDetails = new TemperatureDetails(temperature, humidity, date_time);
        temperatureDatabase.addUserDetails(temperatureDetails);

        if (Integer.valueOf(temperature) > 45.0) {

            Random random = new Random();
            int num = random.nextInt(999999999);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setAutoCancel(true);
            notification.setDefaults(NotificationCompat.DEFAULT_ALL);
            notification.setWhen(System.currentTimeMillis());
            notification.setSmallIcon(R.drawable.fire);
            notification.setContentTitle("Fire Alarm");
            notification.setContentText("Warning..Your Home Temperature is at : " + temperature);

            Intent intent = new Intent(this, TemperatureActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pi = PendingIntent.getActivity(this, num, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pi);

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (nm != null) {
                nm.notify(num, notification.build());
            }
        } else {
            Intent i = new Intent("UPDATE_UI");
            sendBroadcast(i);
        }
    }

}
