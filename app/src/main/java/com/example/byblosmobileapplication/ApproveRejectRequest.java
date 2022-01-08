package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ApproveRejectRequest extends AppCompatActivity {
    ListView listView, listViewApproved, listViewRejected;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<String> approvedRequest = new ArrayList<>();
    ArrayList<String> rejectedRequest = new ArrayList<>();
    DatabaseReference myRefItems;
    String userName;
    HashMap<String,String> hash = new HashMap<String, String>();
    EditText rowNumber;


    public final static String EXTRA_REQUEST = "com.example.byblosmobileapplication.EXTRAREQUEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_request);
        rowNumber = findViewById(R.id.editTextRowNumber);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        //Retriving Request
        listView = (ListView) findViewById(R.id.LISTVIEW);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
        ArrayAdapter<String> approveRequestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, approvedRequest);
        ArrayAdapter<String> rejectRequestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rejectedRequest);
        listView.setAdapter(approveRequestAdapter);

        Spinner spinner=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.status, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String status = adapterView.getItemAtPosition(i).toString();
                if(status.equals("Approved")){
                    listView.setAdapter(approveRequestAdapter);
                }
                else if(status.equals("Rejected")){
                    listView.setAdapter(rejectRequestAdapter);
                }
                else{
                    listView.setAdapter(myArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        myRefItems = FirebaseDatabase.getInstance().getReference("/Service Requests/"+userName);
        myRefItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                approvedRequest.clear();
                rejectedRequest.clear();
                myList.clear();
                ArrayList<HashMap<String, String>> tempList = (ArrayList<HashMap<String, String>>) snapshot.getValue();
                String status;
                String typeService;
                String clientUserName;
                String value;
                HashMap<String, String> hashMap;
                if(tempList == null){
                    return;
                }
                for(int i = 0; i< tempList.size(); i++){
                    hashMap = tempList.get(i);
                    status = hashMap.remove("status");
                    typeService = hashMap.remove("serviceName");
                    clientUserName = hashMap.remove("userName");
                    value = "Service Type:  "+typeService+"\n"+"Customer's Username:    " +clientUserName+"\nRequired Info:   "+hashMap.toString()+ "\n";
                    if(status.equals("Approved")){
                        approvedRequest.add(value);
                        approveRequestAdapter.notifyDataSetChanged();
                    }
                    else if(status.equals("Reject")){
                        rejectedRequest.add(value);
                        rejectRequestAdapter.notifyDataSetChanged();
                    }
                    else if(status.equals("")){
                        hash.put(value, Integer.toString(i));
                        myList.add(value);
                        myArrayAdapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickApprove(View view){
        try{
            String myInt = hash.get(myList.get(Integer.parseInt(rowNumber.getText().toString())-1));
            myRefItems = FirebaseDatabase.getInstance().getReference().child("Service Requests").child(userName).child(myInt).child("status");
            myRefItems.setValue("Approved");
            Intent intent = new Intent(this, BranchPage.class);
            intent.putExtra("userName", userName);
            startActivity(intent);

        }
        catch(Exception e){
            rowNumber.setError("Enter an integer starting from 1");
        }
    }

    public void onClickReject(View view){
        try{
            String myInt = hash.get(myList.get(Integer.parseInt(rowNumber.getText().toString())-1));
            myRefItems = FirebaseDatabase.getInstance().getReference().child("Service Requests").child(userName).child(myInt).child("status");
            myRefItems.setValue("Reject");
            Intent intent = new Intent(this, BranchPage.class);
            intent.putExtra("userName", userName);
            startActivity(intent);

        }
        catch(Exception e){
            rowNumber.setError("Enter an integer starting from 1");
        }
    }


    public void onClickHome(View view){
        Intent intent = new Intent(this, BranchPage.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }


}