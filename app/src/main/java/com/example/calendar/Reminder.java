package com.example.calendar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class Reminder extends BroadcastReceiver {
    SQLiteDataBqase sqLiteDataBqase;
    Cursor cursor;
    Integer ID;
    Integer Reminder;
    Integer Notifi;
    Integer Vibrate;
    String Title;
    String Location;
    String Start;
    String End;

    @Override
    public void onReceive(Context context, Intent intent) {
        ID = intent.getIntExtra("requestCode", 1);
        sqLiteDataBqase = new SQLiteDataBqase(context);
        cursor = sqLiteDataBqase.getallrecordsbyID(String.valueOf(ID));
        cursor.moveToFirst();
        Reminder = cursor.getInt(17);
        Notifi = cursor.getInt(18);
        Vibrate = cursor.getInt(19);
        Title = cursor.getString(1);
        Location = cursor.getString(3);
        Start = String.valueOf(cursor.getInt(6)) + "/" +
                String.valueOf(cursor.getInt(5)) + "/" + String.valueOf(cursor.getInt(4)) +
                "_" + String.valueOf(cursor.getInt(10)) + ":" + String.valueOf(cursor.getInt(11)) +
                " " + cursor.getString(12);
        End = String.valueOf(cursor.getInt(9)) + "/" +
                String.valueOf(cursor.getInt(8)) + "/" + String.valueOf(cursor.getInt(7)) +
                "_" + String.valueOf(cursor.getInt(13)) + ":" + String.valueOf(cursor.getInt(14)) +
                " " + cursor.getString(15);
        if (Reminder == 1) {
            if (Notifi == 1 && Vibrate == 1) {
                Notify(context);
                Vibrate(context);
            } else if (Notifi == 1) {
                Notify(context);
            } else if (Vibrate == 1) {
                Toast.makeText(context, R.string.event + Title + R.string.locationreminder + Location + R.string.timenow, Toast.LENGTH_LONG).show();
                Vibrate(context);
            } else {
                Toast.makeText(context, R.string.event + Title + R.string.locationreminder + Location + R.string.timenow, Toast.LENGTH_LONG).show();
            }
        }

    }

    public void Vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        long[] c = {0, 1000};
        vibrator.vibrate(c, -1);
    }

    public void createchannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("channel1", "channel 1", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This Is Channel 1");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void Notify(Context context) {
        NotificationManagerCompat notificationManagerCompat;
        notificationManagerCompat = NotificationManagerCompat.from(context);
        createchannel(context);
        Intent intent = new Intent(context, MainActivity.class);
        Intent intent1 = new Intent(context, Main3Activity.class);
        Intent intent2 = new Intent(context, RemoveNotif.class);
        intent2.putExtra("ID", ID);
        Bundle bundle = new Bundle();
        bundle.putString("ID", String.valueOf(ID));
        intent1.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID, intent, 0);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, ID, intent1, 0);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, ID, intent2, 0);
        Notification notification = new NotificationCompat.Builder(context, "channel1")
                .setSmallIcon(R.drawable.event)
                .setContentTitle(Title)
                .setContentText(Start + "  " + End + " / " + Location)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.add, "Open", pendingIntent1)
                .addAction(R.drawable.add, "Close", pendingIntent2)
                .setAutoCancel(true)
                .build();
        notificationManagerCompat.notify(ID, notification);
    }
}
