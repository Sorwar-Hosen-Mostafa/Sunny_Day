package com.sunny.sunnyday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.sunny.sunnyday.Fragments.PeriodCalenderFragment;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("menuFragment", "periodCalendar");
        notificationIntent.putExtra("again_notify","false");

        Notification notification;
        NotificationManager notificationManager;
        String notification_status;
        String pregnancy_mode_status;
        long[] pattern = {0, 300, 0};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


        notification_status = Utils.getFromPrefs(context,Utils.SETTINGS_PREFERENCES,Utils.NOTIFICATION);
        pregnancy_mode_status = Utils.getFromPrefs(context,Utils.SETTINGS_PREFERENCES,Utils.PREGNANCY_MODE);

        if(notification_status.equals(Utils.STATUS_TRUE) && pregnancy_mode_status.equals(Utils.STATUS_FALSE)) {

            Utils.saveToPrefs(context,Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_TRUE);
            switch (intent.getIntExtra("request_code", 0)) {
                case 100:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today your period can be start.")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 101:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is your second day of period")
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 102:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is your third day of period")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 103:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is your forth day of period")
                            .setTicker("Hello from sunney day!!")
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 104:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is your fifth day of period")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 105:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is your sixth day of period")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 106:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is your seventh day of period")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 200:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is a favourable day for conceive")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 201:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is a favourable day for conceive")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 202:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is a favourable day for conceive")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 203:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is a favourable day for conceive")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 204:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("today is the last favourable day for conceive")
                            .setTicker("Hello from sunney day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;

            }
        }



    }
}
