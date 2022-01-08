package com.example.byblosmobileapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BranchList extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;

    public BranchList(Activity context, List<Branch> branches) {
        super(context,R.layout.layout_branches_list, branches);
        this.context = context;
        this.branches = branches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_branches_list, null, true);

        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);
        TextView textViewWDhours = (TextView) listViewItem.findViewById(R.id.textViewWDHours);
        TextView textViewWKhours = (TextView) listViewItem.findViewById(R.id.textViewWKHours);
        TextView textViewEmployee = (TextView) listViewItem.findViewById(R.id.textViewEmployee);
        TextView textViewNum = (TextView) listViewItem.findViewById(R.id.textViewNumber);
        TextView textViewRate = (TextView) listViewItem.findViewById(R.id.textViewRate);

        Branch newBranch = branches.get(position);
        textViewAddress.setText("Branch Address: "+newBranch.getAddress());
        String str="Weekday hours: "+newBranch.getStartTimeWeekday()+" - "+newBranch.getEndTimeWeekday();
        String str2="Weekend hours: "+newBranch.getStartTimeWeekend()+" - "+newBranch.getEndTimeWeekend();
        textViewWDhours.setText(str);
        textViewWKhours.setText(str2);
        textViewEmployee.setText("Employee: "+newBranch.getEmployee());
        textViewNum.setText("Phone number: "+newBranch.getPhoneNumber());
        return listViewItem;
    }
}
