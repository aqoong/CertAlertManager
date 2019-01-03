package com.example.chinae.alertmanager.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chinae.alertmanager.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context         mContext = null;
    private ArrayList<Data> projList = null;


    public ListAdapter(Context context, ArrayList<Data> list){
        mContext = context;
        projList = list;
    }

    @Override
    public int getCount() {
        return projList.size();
    }

    @Override
    public Object getItem(int position) {
        return projList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
        }

        TextView index      = convertView.findViewById(R.id.item_index);
        TextView name       = convertView.findViewById(R.id.item_proj_name);
        TextView regiDate   = convertView.findViewById(R.id.item_cert_date_regi);
        TextView endDate    = convertView.findViewById(R.id.item_cert_date_end);

        index.setText("" + (position + 1));
        name.setText(projList.get(position).getStrProjName());
        regiDate.setText(projList.get(position).getDtCertRegi());
        endDate.setText(projList.get(position).getDtCertEnd());


        return convertView;
    }


}
