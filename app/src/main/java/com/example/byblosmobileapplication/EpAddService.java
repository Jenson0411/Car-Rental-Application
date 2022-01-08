package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EpAddService extends AppCompatActivity {

    EditText serviceName;
    ListView listviewServices;
    ListView listViewAdServices;
    private String userName;
    ArrayList<ServiceClass> serviceList;
    ArrayList<ServiceClass> serviceListAdmin;
    DatabaseReference databaseEmServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_add_service);
        Intent intent = getIntent();
        getServiceList();
        userName= intent.getStringExtra("userName");
        listViewAdServices=findViewById(R.id.AdListView);
        ServiceList2 adServicesAdapter=new ServiceList2(EpAddService.this,serviceListAdmin);
        listViewAdServices.setAdapter(adServicesAdapter);
    }


    public void getServiceList(){
        serviceListAdmin=new ArrayList<ServiceClass>();
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addService(View  view){
        databaseEmServices=FirebaseDatabase.getInstance().getReference("EmService/"+userName);
        serviceName=findViewById(R.id.editEmName);
        String sName=serviceName.getText().toString();
        if(sName.isEmpty()){
            serviceName.setError("Please enter valid service name");
            serviceName.setText("");
            return;
        }

        for(ServiceClass service:serviceList){
            if(service.getServiceName().equals(sName)){
                Toast.makeText(this,"Service has already existed",Toast.LENGTH_SHORT).show();
                serviceName.setText("");
                return;
            }
        }

        for(ServiceClass service:serviceListAdmin){
            if(service.getServiceName().equals(sName)){
                databaseEmServices.child(sName).setValue(service);
                Toast.makeText(this,"Service added",Toast.LENGTH_SHORT).show();
                serviceName.setText("");
                return;
            }
        }
        Toast.makeText(this,"Service is not offered by Admin",Toast.LENGTH_SHORT).show();
        serviceName.setText("");
    }


    @Override
    protected void onStart() {
        super.onStart();
        listviewServices=findViewById(R.id.EmListView);
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

                ServiceList servicesAdapter=new ServiceList(EpAddService.this,serviceList);

                listviewServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void deleteService(String serviceName){

        DatabaseReference dService = FirebaseDatabase.getInstance().getReference("EmService/"+userName).child(serviceName);
        dService.removeValue();
        Toast.makeText(this,"Service deleted",Toast.LENGTH_SHORT).show();
    }



    public void onClickDelete(View view){
        serviceName=findViewById(R.id.editEmName);
        String sName=serviceName.getText().toString();
        for(ServiceClass service:serviceList){
            if(service.getServiceName().equals(sName)){
                deleteService(sName);
                serviceName.setText("");
                return;
            }
        }

        Toast.makeText(this,"Service doesn't exist",Toast.LENGTH_SHORT).show();
        serviceName.setText("");
    }

    public void backBnt(View view){
        Intent intent = new Intent(this, BranchPage.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}