package com.example.chinae.alertmanager.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BackupManager {
    private Context mContext = null;

    public BackupManager(Context context){
        mContext = context;
    }

    public boolean sendSMS(String tel, String message){
        boolean result = false;

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , tel);
        smsIntent.putExtra("sms_body"  , message);

        try {
            mContext.startActivity(smsIntent);
            result = true;
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

}
