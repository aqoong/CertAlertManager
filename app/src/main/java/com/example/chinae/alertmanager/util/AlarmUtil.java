package com.example.chinae.alertmanager.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

        //한달전
        long millDate = alarmDate.getTime() - 2635200000L;


        //하루전
//        millDate = alarmDate.getTime() - 129600000L;

        Log.i("AlaramUtil", ""+alarmDate.getTime());

        Date testDate = new Date(millDate);
        Log.i("AlaramUtil", testDate.toString());
        alarmManager.setExact(AlarmManager.RTC, millDate, pIntent);


//        alarmManager.setExact(AlarmManager.RTC, System.currentTimeMillis() + 2000, pIntent);
    }
}
