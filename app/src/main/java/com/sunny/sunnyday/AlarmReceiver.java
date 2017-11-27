package com.sunny.sunnyday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


public class AlarmReceiver extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


        notification_status = Utils.getFromPrefs(context,Utils.SETTINGS_PREFERENCES,Utils.NOTIFICATION);
        pregnancy_mode_status = Utils.getFromPrefs(context,Utils.SETTINGS_PREFERENCES,Utils.PREGNANCY_MODE);

        if(notification_status.equals(Utils.STATUS_TRUE) && pregnancy_mode_status.equals(Utils.STATUS_FALSE)) {

            Utils.saveToPrefs(context,Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_TRUE);
            switch (intent.getIntExtra("request_code", 0)) {
                case 100:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("Your period might start today,");
                    inboxStyle.addLine("please take precaution!");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setStyle(inboxStyle)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();



                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 101:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are one day late for your current cycle,");
                    inboxStyle.addLine("want to log your period?");
                    notification = builder.setContentTitle("How is your day?")
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setStyle(inboxStyle)
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 102:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are 2 days late for your current cycle,");
                    inboxStyle.addLine("want to log your period?");

                    
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setStyle(inboxStyle)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 103:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are late for your current cycle,");
                    inboxStyle.addLine("want to log your period?");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setStyle(inboxStyle)
                            .setSound(alarmSound)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 104:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are late for your current cycle,");
                    inboxStyle.addLine("want to log your period?");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setStyle(inboxStyle)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 105:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are late for your current cycle,");
                    inboxStyle.addLine("want to log your period?");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setStyle(inboxStyle)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 106:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are late for your current cycle,");
                    inboxStyle.addLine("want to log your period?");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setStyle(inboxStyle)
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 200:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("Hey! Your Ovulation has started,");
                    inboxStyle.addLine("it's the lucky time!");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setStyle(inboxStyle)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 201:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("There is a chance to get pregnant,");
                    inboxStyle.addLine("try today!");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setStyle(inboxStyle)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 202:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("You are the most fertile today,");
                    inboxStyle.addLine("there is a high chance to get pregnant!");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setStyle(inboxStyle)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 203:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("If you haven't tried yet, don't miss the chance!");
                    inboxStyle.addLine("It's still a fertile day!");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setSound(alarmSound)
                            .setStyle(inboxStyle)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    break;
                case 204:
                    inboxStyle.setBigContentTitle("How is your day?");
                    inboxStyle.addLine("Last day to get pregnant on this cycle,");
                    inboxStyle.addLine("don't miss it!");
                    notification = builder.setContentTitle("How is your day?")
                            .setTicker("Hello from sunny day!!")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(pattern)
                            .setStyle(inboxStyle)
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
