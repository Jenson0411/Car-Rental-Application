package com.example.byblosmobileapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ServiceList2 extends ArrayAdapter<ServiceClass> {
    private Activity context;
    List<ServiceClass> services;

    public ServiceList2(Activity context, List<ServiceClass> services) {
        super(context,R.layout.layout_service_list2, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_service_list2, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewSSName);

        ServiceClass newservice = services.get(position);
        textViewName.setText("Service Name: "+newservice.getServiceName());

        return listViewItem;
    }
}
