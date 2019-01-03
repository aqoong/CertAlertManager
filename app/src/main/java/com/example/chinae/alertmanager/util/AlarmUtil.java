package com.example.chinae.alertmanager.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class AlarmUtil {
    private AlarmUtil() {}

    private static class Holder{
        public static final AlarmUtil INSTANCE = new AlarmUtil();
    }

    public static AlarmUtil getInstance() {
        return Holder.INSTANCE;
    }


    public void setAlarm(Context context, Date alarmDate){
        Intent intent = new Intent(context , AlarmReceiver.class);
        PendingIntent pIntent =  PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC, alarmDate.getTime() - 129600000, pIntent);
//        alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + 2000, pIntent);
    }
}
