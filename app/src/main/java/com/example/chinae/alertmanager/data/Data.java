package com.example.chinae.alertmanager.data;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  Project data info
 */
public class Data {
    private String  strProjName = null;
    private Date    dtCertRegi  = null;     //server cert regist date
    private Date    dtCertEnd   = null;     //server cert end date

    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    public String getStrProjName() {
        return strProjName;
    }

    public void setStrProjName(String strProjName) {
        this.strProjName = strProjName;
    }

    public String getDtCertRegi() {
        return format.format(dtCertRegi);
    }

    public Date getDtCertRegiDate(){
        return dtCertRegi;
    }

    public void setDtCertRegi(String date) {

        this.dtCertRegi = stringToDate(date);
    }

    public String getDtCertEnd() {
        return format.format(dtCertEnd);
    }

    public Date getDtCertEndDate(){
        return dtCertEnd;
    }

    public void setDtCertEnd(String date) {

        this.dtCertEnd = stringToDate(date);
    }

    private Date stringToDate(String input){
        Date result = null;

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            result = transFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
