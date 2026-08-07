package com.example.d424booktracker.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.d424booktracker.R;

public class MyReceiver extends BroadcastReceiver {

    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("title") + intent.getStringExtra("key"))
                .setContentTitle("NotificationTest").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n);
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID){
        //excursion notifications
        CharSequence name = "excursion notifications";
        String description = "notifications for date of excursions";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        //vacation start notifications
        CharSequence name1 = "vacation start notifications";
        String description1 = "notifications for the start date of vacations";
        String CHANNEL1_ID = "CHANNEL1_ID";


        NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, name1, importance);
        channel1.setDescription(description1);
        notificationManager.createNotificationChannel(channel1);

        //vacation end notifications
        CharSequence name2 = "vacation end notifications";
        String description2 = "notifications for the end date of vacations";
        String CHANNEL2_ID = "CHANNEL2_ID";


        NotificationChannel channel2 = new NotificationChannel(CHANNEL2_ID, name2, importance);
        channel1.setDescription(description2);
        notificationManager.createNotificationChannel(channel2);
    }

}
