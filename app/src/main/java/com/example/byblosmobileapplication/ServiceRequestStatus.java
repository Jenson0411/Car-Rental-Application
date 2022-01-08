package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceRequestStatus extends AppCompatActivity {
    ListView listView;
    ArrayList<String> approvedRequest = new ArrayList<>();
    ArrayList<String> rejectedRequest = new ArrayList<>();
    ArrayList<String> myList = new ArrayList<>();
    DatabaseReference myRefItems = FirebaseDatabase.getInstance().getReference().child("Service Requests");
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_status);
        listView = findViewById(R.id.listCustomerRequests);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, approvedRequest);
        ArrayAdapter<String> myArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rejectedRequest);
        ArrayAdapter<String> myArrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(myArrayAdapter);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userAccount");
        System.out.println(userName);

        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.status, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String status = adapterView.getItemAtPosition(i).toString();
                if(status.equals("Approved")){
                    listView.setAdapter(myArrayAdapter);
                }
                else if(status.equals("Rejected")){
                    listView.setAdapter(myArrayAdapter2);
                }
                else{
                    listView.setAdapter(myArrayAdapter3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myRefItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList.clear();
                approvedRequest.clear();
                rejectedRequest.clear();
                ArrayList<HashMap<String,String>> tempList;
                String status;
                String typeService;
                String clientUserName;
                String value;
                String employeeUserName;
                HashMap<String, String> hashMap;
                for(DataSnapshot item: snapshot.getChildren()){
                    tempList = (ArrayList<HashMap<String, String>>) item.getValue();
                    employeeUserName = item.getKey();
                    for(int i = 0; i<tempList.size();i++){
                        hashMap = tempList.get(i);
                        status = hashMap.remove("status");
                        typeService = hashMap.remove("serviceName");
                        clientUserName = hashMap.remove("userName");
                        value = "Service Type:  "+typeService+"\nBranch account:"+employeeUserName+"\nRequired Info:   "+hashMap.toString()+ "\n";
                        if(status.equals("Approved") && clientUserName.equals(userName)){
                            approvedRequest.add(value);
                        }
                        else if(status.equals("Reject")&& clientUserName.equals(userName)){
                            rejectedRequest.add(value);
                        }
                        else if(status.equals("")&& clientUserName.equals(userName)){
                            myList.add(value);
                        }
                    }
                }
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickHomeStatusRequestService(View view){
        finish();
        Intent intent = new Intent(this, CustomerPage.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}