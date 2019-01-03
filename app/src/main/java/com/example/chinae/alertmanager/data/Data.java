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

    SimpleDateFormat format = new SimpleDateFormat("yyyy.mm.dd");

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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");

        try {
            this.dtCertRegi = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDtCertEnd() {
        return format.format(dtCertEnd);
    }

    public Date getDtCertEndDate(){
        return dtCertEnd;
    }

    public void setDtCertEnd(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");
        try {
            this.dtCertEnd = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
