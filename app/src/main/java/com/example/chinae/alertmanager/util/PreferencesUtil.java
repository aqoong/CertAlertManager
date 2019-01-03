package com.example.chinae.alertmanager.util;


import android.util.Log;

import com.example.chinae.alertmanager.data.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.chinae.alertmanager.data.Global.KEY_PROJ_END_DT;
import static com.example.chinae.alertmanager.data.Global.KEY_PROJ_NAME;
import static com.example.chinae.alertmanager.data.Global.KEY_PROJ_REGI_DT;

/**
 *  SharedPreferences Util
 *
 *  * JSON String save
 *  * String to JSON & Data
 */
public class PreferencesUtil {
    private final String TAG = "PreferencesUtil";


    /**
     *  ArrayList 를 String 으로 변경
     *  (중간에 json으로 변경한 뒤 String으로 변환)
     * @param arrayList
     * @return
     */
    public String arrayToString(ArrayList<Data> arrayList){
        JSONArray jsonArray = new JSONArray();

        for(Data temp : arrayList){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(KEY_PROJ_NAME, temp.getStrProjName());
                jsonObject.put(KEY_PROJ_REGI_DT, temp.getDtCertRegi().replace(".", ""));
                jsonObject.put(KEY_PROJ_END_DT, temp.getDtCertEnd().replace(".",""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }

        Log.d(TAG, jsonArray.toString());


        return jsonArray.toString();
    }

    /**
     * Adapter에 연결되어 있는 ArrayList에 JSON형태의 String을 변환하여 담아준다
     * @param array
     * @param datas
     * @return
     */
    public ArrayList<Data> stringToArray(ArrayList<Data> array ,String datas){

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(datas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonArray == null){
            return null;
        }

        array.clear();

        ////// jsonarray -> arraylist

        for(int i = 0 ; i < jsonArray.length() ; i++){
            JSONObject jsonObject = null;
            Data tempData = new Data();

            try {
                jsonObject = jsonArray.getJSONObject(i);

                tempData.setStrProjName(jsonObject.getString(KEY_PROJ_NAME));
                tempData.setDtCertRegi(jsonObject.getString(KEY_PROJ_REGI_DT));
                tempData.setDtCertEnd(jsonObject.getString(KEY_PROJ_END_DT));

                array.add(tempData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return array;
    }
}
