package com.example.chinae.alertmanager;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chinae.alertmanager.data.BackupManager;
import com.example.chinae.alertmanager.data.Data;
import com.example.chinae.alertmanager.data.ListAdapter;
import com.example.chinae.alertmanager.util.AlarmReceiver;
import com.example.chinae.alertmanager.util.AlarmUtil;
import com.example.chinae.alertmanager.util.PreferencesUtil;

import java.util.ArrayList;

import static com.example.chinae.alertmanager.data.Global.KEY;
import static com.example.chinae.alertmanager.data.Global.PREDATAS;

public class MainActivity extends AppCompatActivity {

    private Context         mContext        = null;

    private ListView        listView        = null;
    private ArrayList<Data> dataList        = null;
    private ListAdapter dataListAdapter     = null;

    private PreferencesUtil dataUtil        = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        dataList = new ArrayList<>();

        //list adapter
        dataListAdapter = new ListAdapter(mContext, dataList);
        dataUtil = new PreferencesUtil();

        //화면 셋팅
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //아이템 하나 롱클릭시 삭제 기능
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                dialogBuilder.setMessage("삭제하시겠습니까?");
                dialogBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataList.remove(position);
                        dataListAdapter.notifyDataSetChanged();
                    }
                });
                dialogBuilder.show();
                return false;
            }
        });
        listView.setAdapter(dataListAdapter);

    }


    @Override
    protected void onStop() {
        //앱 멈춤이 발생하면 저장소에 저장
        SharedPreferences sharedPreferences = getSharedPreferences(PREDATAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY, dataUtil.arrayToString(dataList));
        editor.commit();

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        //앱이 실행 되는 순간 저장소로 부터 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences(PREDATAS, MODE_PRIVATE);
        if(sharedPreferences.contains(KEY)){
            dataUtil.stringToArray(dataList, sharedPreferences.getString(KEY, ""));

            for(Data temp : dataList){
                //alarm setting
                AlarmUtil.getInstance().setAlarm(mContext, temp.getDtCertEndDate());
            }

            dataListAdapter.notifyDataSetChanged();
        }
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {


        super.onResume();
    }


    //Activity Option Menu setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_add:
                //show add dialog
                openAddDialog(mContext);

                break;
            case R.id.menu_clear:dd
                dataList.clear();
                dataListAdapter.notifyDataSetChanged();
                break;

            case R.id.menu_backup:
                //show backup dialog
                openBackupDialog();
                break;
            case R.id.menu_restore:
                //show restore dialog
                openRestoreDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void openAddDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_item_add);


        final EditText tmpProjName = ((EditText)dialog.findViewById(R.id.dialog_name));
        final EditText tmpRegiDate = ((EditText)dialog.findViewById(R.id.dialog_regi_date));
        final EditText tmpEndDate = ((EditText)dialog.findViewById(R.id.dialog_end_date));


        View.OnClickListener dialogClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btn_add){

                    Data tempData = new Data();
                    tempData.setStrProjName(tmpProjName.getText().toString());
                    tempData.setDtCertRegi(tmpRegiDate.getText().toString());
                    tempData.setDtCertEnd(tmpEndDate.getText().toString());

                    //alarm setting
                    AlarmUtil.getInstance().setAlarm(mContext, tempData.getDtCertEndDate());

                    dataList.add(tempData);
                    dialog.dismiss();
                }
                else if(v.getId() == R.id.btn_cancel){
                    dialog.dismiss();
                }
            }
        };

        dialog.findViewById(R.id.btn_add).setOnClickListener(dialogClickListener);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(dialogClickListener);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dataListAdapter.notifyDataSetChanged();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void openBackupDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("백업");
        dialog.setContentView(R.layout.dialog_backup);

        final EditText tel = dialog.findViewById(R.id.dialog_phone_num);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_add:
                        BackupManager backupManager = new BackupManager(mContext);
                        if(!backupManager.sendSMS(tel.getText().toString(), dataUtil.arrayToString(dataList)))
                        {
                            Toast.makeText(mContext, "전송실패", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                        break;
                    case R.id.btn_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        Button btnSend = dialog.findViewById(R.id.btn_add);
        btnSend.setText("SEND");
        btnSend.setOnClickListener(onClickListener);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(onClickListener);
        dialog.show();
    }

    private void openRestoreDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("복원");
        dialog.setContentView(R.layout.dialog_restore);

        final EditText content = dialog.findViewById(R.id.dialog_content);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_add:

                        dataUtil.stringToArray(dataList, content.getText().toString());

                        for(Data temp : dataList){
                            //alarm setting
                            AlarmUtil.getInstance().setAlarm(mContext, temp.getDtCertEndDate());
                        }

                        dataListAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                        break;
                    case R.id.btn_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        Button btnSend = dialog.findViewById(R.id.btn_add);
        btnSend.setText("RESTORE");
        btnSend.setOnClickListener(onClickListener);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(onClickListener);
        dialog.show();
    }

}
