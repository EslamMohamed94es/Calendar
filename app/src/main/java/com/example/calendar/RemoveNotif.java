package com.example.calendar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
public class RemoveNotif extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Integer ID= intent.getIntExtra("ID", 1);
        NotificationManagerCompat notificationManagerCompat;
        notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.cancel(ID);
    }
}
