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
                            .setContentText("Your period might start today, please take precaution!")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 101:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are one day late for your current cycle, want to log your period?")
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 102:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are 2 days late for your current cycle, want to log your period?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 103:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are late for your current cycle, want to log your period?")
                            .setTicker("Hello from sunny day!!")
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setSmallIcon(R.drawable.icon1)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 104:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are late for your current cycle, want to log your period?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 105:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are late for your current cycle, want to log your period?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 106:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are late for your current cycle, want to log your period?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 200:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("Hey! Your Ovulation has started, it's the lucky time!")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 201:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("There is a chance to get pregnant, try today!")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 202:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("You are the most fertile today, there is a high chance to get pregnant!")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 203:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("If you haven't tried yet, don't miss the chance! It's still a fertile day!")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 204:
                    notification = builder.setContentTitle("How is your day??")
                            .setContentText("Last day to get pregnant on this cycle, don't miss it!")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.drawable.icon1)
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
