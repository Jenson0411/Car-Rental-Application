package com.example.byblosmobileapplication;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ServiceList extends ArrayAdapter<ServiceClass> {
    private Activity context;
            List<ServiceClass> services;

    public ServiceList(Activity context, List<ServiceClass> services) {
        super(context,R.layout.layout_services_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_services_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewSName);
        TextView textViewRate = (TextView) listViewItem.findViewById(R.id.textViewHRate);
        TextView textViewDes = (TextView) listViewItem.findViewById(R.id.textViewDescription);

        ServiceClass newservice = services.get(position);
        textViewName.setText(newservice.getServiceName());
        String str="Houly Rate: "+String.valueOf(newservice.getHourlyRate());
        textViewRate.setText(str);

        ArrayList<String> description= newservice.getDescription();
        String des="";
        for(String info:description){
            des+=(info+"; ");
        }
        textViewDes.setText("Required infomation: "+des);
        return listViewItem;
    }
}
