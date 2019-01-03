package com.example.chinae.alertmanager.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.chinae.alertmanager.MainActivity;
import com.example.chinae.alertmanager.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        //Notification setting
        PendingIntent pIntent = PendingIntent.getActivity(context, 0,
                new Intent(context.getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);



        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정하지 않으면 오류남
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle("JT친애저축은행 업무") // 제목 설정
                .setContentText("인증서 갱신 항목이 존재합니다.") // 내용 설정
                .setTicker("JT친애저축은행") // 상태바에 표시될 한줄 출력
                .setAutoCancel(true)
                .setContentIntent(pIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());


        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Toast.makeText(context, "인증서 갱신 항목이 존재합니다", Toast.LENGTH_LONG).show();
    }
}
