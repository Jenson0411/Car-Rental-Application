package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BranchPage extends AppCompatActivity {

    ListView listviewServices;
    ArrayList<ServiceClass> serviceList;
    private String userName;
    Branch branch;
    private TextView address;
    private TextView phoneNum;
    private TextView workingHourWD;
    private TextView workingHourWK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_page);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        branch = new Branch(userName, "", "");
        getBranch(branch);
        address=findViewById(R.id.branchAddress);
        phoneNum=findViewById(R.id.branchPhoneNum);
        workingHourWD=findViewById(R.id.branchWeekdayWorkingHour);
        workingHourWK=findViewById(R.id.branchWeekendWorkingHour);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listviewServices=findViewById(R.id.listViewService);
        serviceList=new ArrayList<ServiceClass>();

        DatabaseReference dataRefEM= FirebaseDatabase.getInstance().getReference("EmService/"+userName);
        dataRefEM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    ServiceClass newService=postSnapshot.getValue(ServiceClass.class);
                    serviceList.add(newService);
                }

                ServiceList servicesAdapter=new ServiceList(BranchPage.this,serviceList);

                listviewServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getBranch(Branch branch){
        DatabaseReference dataRefBranch= FirebaseDatabase.getInstance().getReference("Branches/"+userName);

        dataRefBranch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    if(item.getKey().equals("address")){
                        branch.setAddress(item.getValue().toString());
                    }
                    else if(item.getKey().equals("phoneNumber")){
                        branch.setPhoneNumber(item.getValue().toString());
                    }
                    else if(item.getKey().equals("endTimeWeekend")){
                        branch.setEndTimeWeekend(item.getValue().toString());
                    }
                    else if(item.getKey().equals("startTimeWeekend")){
                        branch.setStartTimeWeekend(item.getValue().toString());
                    }
                    else if(item.getKey().equals("endTimeWeekday")){
                        branch.setEndTimeWeekday(item.getValue().toString());
                    }
                    else if(item.getKey().equals("startTimeWeekday")){
                        branch.setStartTimeWeekday(item.getValue().toString());
                    }
                }
                address.setText("Address: "+branch.getAddress());
                phoneNum.setText("Phone Number: "+branch.getPhoneNumber());
                workingHourWD.setText("Weekday Hours: "+branch.getStartTimeWeekday()+" - "+branch.getEndTimeWeekday());
                workingHourWK.setText("Weekend Hours: "+branch.getStartTimeWeekend()+" - "+branch.getEndTimeWeekend());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickAddServiceEM(View view){
        Intent intent = new Intent(this, EpAddService.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    public void onClickApproveRejectRequest(View view){
        Intent intent = new Intent(this, ApproveRejectRequest.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    public void onClickEmployeeEditProfile(View view){
        Intent intent = new Intent(this, ProfileManager.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    public void onClickSpecifyHours(View view){
        Intent intent = new Intent(this, SpecifyWorkingHours.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
    public void onClickBPSignout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

}