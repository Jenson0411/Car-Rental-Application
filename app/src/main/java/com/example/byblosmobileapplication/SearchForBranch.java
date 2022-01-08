package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchForBranch extends AppCompatActivity {

    EditText searchItem;
    private Spinner date;
    private  String wdOrwk;
    ListView listviewBranches;
    ListView listViewAdServices;
    ArrayList<Branch> branchList;
    ArrayList<Branch> branchList0;
    ArrayList<ServiceClass> serviceListAdmin;
    ArrayList<String> branchNameList;
    public static final Pattern valid_time = Pattern.compile("([01]?[0-9]|2[0-3])[:]{1}[0-5][0-9]?",Pattern.CASE_INSENSITIVE);
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_branch);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userAccount");
        getBranchList();
        getServiceList();

        date=findViewById(R.id.SFspinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.date, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        date.setAdapter(adapter);
        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wdOrwk = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listviewBranches=findViewById(R.id.SFListView);
        searchItem=findViewById(R.id.editSFET);
        Button searchByAddress=findViewById(R.id.SFAddressButton);
        Button searchByHours=findViewById(R.id.SFHoursButton);
        Button searchByService=findViewById(R.id.SFServicesButton);

        searchByAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword=searchItem.getText().toString();
                validationByAddress(keyword);
                getBranchListByAddress(keyword);
                searchItem.setText("");
            }
        });

        searchByHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hour=searchItem.getText().toString();
                if(!validationByHour(hour)){
                    return;
                }
                if(wdOrwk.equals("Weekday")){
                    getBranchListByHour(hour);
                }
                else{
                    getBranchListByHour2(hour);
                }
                searchItem.setText("");
            }
        });

        searchByService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String service=searchItem.getText().toString();
                validationByService(service);
                getBranchListByService(service);
                searchItem.setText("");
            }
        });

        listviewBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(SearchForBranch.this, DisplayBranchServices.class);
                Branch branch = (Branch) listviewBranches.getItemAtPosition(i);
                intent1.putExtra("employeeAccount",branch.getEmployee());
                intent1.putExtra("userAccount", userName);
                startActivity(intent1);
            }
        });
    }

    public void validationByAddress(String keyword){
        boolean isValid=false;

        if(keyword.isEmpty()){
            searchItem.setError("Please enter the keyword");
        }
        for(Branch branch:branchList0){
            if(branch.getAddress().equals(keyword)){
                isValid=true;
                break;
            }
        }
        if(!isValid){
            Toast.makeText(this,"The branch address doesn't exist",Toast.LENGTH_SHORT).show();
            searchItem.setText("");
        }
    }

    public void getBranchListByAddress(String address){
        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");
        branchList=new ArrayList<Branch>();

        dataRefBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    if(postSnapshot.child("address").getValue().equals(address)){
                        Branch branch=postSnapshot.getValue(Branch.class);
                        branchList.add(branch);
                    }
                }
                BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
                listviewBranches.setAdapter(branchesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean validationByHour(String keyword){
        boolean isValid=true;
        Matcher timeMatcher = valid_time.matcher(keyword.trim());
        if(keyword.isEmpty()){
            searchItem.setError("Please enter the keyword");
            isValid=false;
        }
        else if(!timeMatcher.find()){
            searchItem.setError("Please enter valid format, for example: xx:xx");
            searchItem.setText("");
            isValid=false;
        }
        return isValid;
    }

    public void getBranchListByHour(String hour){
        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");
        branchList=new ArrayList<Branch>();
        String[]  validtime;

        try{
            validtime=hour.split(":");

        }
        catch (Exception e){
            BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
            listviewBranches.setAdapter(branchesAdapter);
            return;
        }

        if(Integer.parseInt(validtime[0])>=24||Integer.parseInt(validtime[0])<0){
            searchItem.setError("Please enter valid hour, the hour must be less than 24 and greater than 0");
            searchItem.setText("");
            BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
            listviewBranches.setAdapter(branchesAdapter);
            return;
        }
        if(Integer.parseInt(validtime[1])>=60||Integer.parseInt(validtime[1])<0){
            searchItem.setError("Please enter valid minute, the minute must be less than 60 and great than 0");
            searchItem.setText("");
            BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
            listviewBranches.setAdapter(branchesAdapter);
            return;
        }

        dataRefBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] startTime;
                String[] endTime;
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    startTime=((String)postSnapshot.child("startTimeWeekday").getValue()).split(":");
                    endTime=((String)postSnapshot.child("endTimeWeekday").getValue()).split(":");
                    if(Integer.parseInt(startTime[0])<Integer.parseInt(validtime[0])&&Integer.parseInt(validtime[0])<Integer.parseInt(endTime[0])){
                        Branch branch=postSnapshot.getValue(Branch.class);
                        branchList.add(branch);
                    }
                    else if(Integer.parseInt(startTime[0])==Integer.parseInt(validtime[0])){
                        if(Integer.parseInt(startTime[1])<=Integer.parseInt(validtime[1])){
                            Branch branch=postSnapshot.getValue(Branch.class);
                            branchList.add(branch);
                        }
                    }
                    else if(Integer.parseInt(endTime[0])==Integer.parseInt(validtime[0])){
                        if(Integer.parseInt(endTime[1])>Integer.parseInt(validtime[1])){
                            Branch branch=postSnapshot.getValue(Branch.class);
                            branchList.add(branch);
                        }
                    }
                }
                BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
                listviewBranches.setAdapter(branchesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getBranchListByHour2(String hour){
        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");
        branchList=new ArrayList<Branch>();
        String[]  validtime;

        try{
            validtime=hour.split(":");
        }
        catch (Exception e){
            BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
            listviewBranches.setAdapter(branchesAdapter);
            return;
        }

        if(Integer.parseInt(validtime[0])>=24||Integer.parseInt(validtime[0])<0){
            searchItem.setError("Please enter valid hour, the hour must be less than 24 and greater than 0");
            searchItem.setText("");
            BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
            listviewBranches.setAdapter(branchesAdapter);
            return;
        }
        if(Integer.parseInt(validtime[1])>=60||Integer.parseInt(validtime[1])<0){
            searchItem.setError("Please enter valid minute, the minute must be less than 60 and great than 0");
            searchItem.setText("");
            BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
            listviewBranches.setAdapter(branchesAdapter);
            return;
        }

        dataRefBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] startTime;
                String[] endTime;
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    startTime=((String)postSnapshot.child("startTimeWeekend").getValue()).split(":");
                    endTime=((String)postSnapshot.child("endTimeWeekend").getValue()).split(":");
                    if(Integer.parseInt(startTime[0])<Integer.parseInt(validtime[0])&&Integer.parseInt(validtime[0])<Integer.parseInt(endTime[0])){
                        Branch branch=postSnapshot.getValue(Branch.class);
                        branchList.add(branch);
                    }
                    else if(Integer.parseInt(startTime[0])==Integer.parseInt(validtime[0])){
                        if(Integer.parseInt(startTime[1])<=Integer.parseInt(validtime[1])){
                            Branch branch=postSnapshot.getValue(Branch.class);
                            branchList.add(branch);
                        }
                    }
                    else if(Integer.parseInt(endTime[0])==Integer.parseInt(validtime[0])){
                        if(Integer.parseInt(endTime[1])>Integer.parseInt(validtime[1])){
                            Branch branch=postSnapshot.getValue(Branch.class);
                            branchList.add(branch);
                        }
                    }
                }
                BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
                listviewBranches.setAdapter(branchesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void validationByService(String keyword){
        boolean isValid=false;

        if(keyword.isEmpty()){
            searchItem.setError("Please enter the keyword");
        }
        for(ServiceClass service:serviceListAdmin){
            if(service.getServiceName().equals(keyword)){
                isValid=true;
                break;
            }
        }
        if(!isValid){
            Toast.makeText(this,"The service doesn't exist",Toast.LENGTH_SHORT).show();
            searchItem.setText("");
        }

    }

    public void getBranchListByService(String service){
//        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
//        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");
        FirebaseDatabase databaseService = FirebaseDatabase.getInstance();
        DatabaseReference dataRefService= databaseService.getReference().child("EmService");
        branchList=new ArrayList<Branch>();
        branchNameList=new ArrayList<String>();

        dataRefService.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchNameList.clear();
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    for(DataSnapshot serviceSnapshot: postSnapshot.getChildren()){
                        if(serviceSnapshot.getKey().equals(service)){
                            branchNameList.add(postSnapshot.getKey());
                        }
                    }
                }
                getBranch();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        dataRefBranches.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot postSnapshot:snapshot.getChildren()){
//                    for(String branchName:branchNameList){
//                        if(postSnapshot.getKey().equals(branchName)){
//                            Branch branch=postSnapshot.getValue(Branch.class);
//                            branchList.add(branch);
//                        }
//                    }
//                }
//
//                BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);
//                listviewBranches.setAdapter(branchesAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void getBranch(){
        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");
        dataRefBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchList.clear();
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    for(String branchName:branchNameList){
                        if(postSnapshot.getKey().equals(branchName)){
                            Branch branch=postSnapshot.getValue(Branch.class);
                            branchList.add(branch);
                            System.out.println(branchList);

                        }
                    }
                }

                BranchList branchesAdapter=new BranchList(SearchForBranch.this,branchList);


                listviewBranches.setAdapter(branchesAdapter);
                branchesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getBranchList(){
        branchList0=new ArrayList<Branch>();
        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");

        dataRefBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchList0.clear();
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    Branch newBranch=postSnapshot.getValue(Branch.class);
                    branchList0.add(newBranch);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getServiceList(){
        serviceListAdmin=new ArrayList<ServiceClass>();
        listViewAdServices=findViewById(R.id.SFAdListView);
        FirebaseDatabase databaseAdmin = FirebaseDatabase.getInstance();
        DatabaseReference dataRefAdmin= databaseAdmin.getReference().child("Services");

        dataRefAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceListAdmin.clear();
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    ServiceClass newService=postSnapshot.getValue(ServiceClass.class);
                    serviceListAdmin.add(newService);
                }

                ServiceList2 adServicesAdapter=new ServiceList2(SearchForBranch.this,serviceListAdmin);
                listViewAdServices.setAdapter(adServicesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void SFbackBnt(View view){
        Intent intent = new Intent(this, CustomerPage.class);
        intent.putExtra("userName", userName);
        finish();
        startActivity(intent);
    }
}