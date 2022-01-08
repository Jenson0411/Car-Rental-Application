package com.example.byblosmobileapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteService extends AppCompatActivity {

    private EditText sName;
    private ListView listviewServices;
    private ArrayList<ServiceClass> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service);

        listviewServices=findViewById(R.id.DeleteListView);
    }

    //idea for this file: have one button with 2 text boxes to delete a specific requirement in a specific service
    // and another button to delete an entire service
    public void onClickDeleteService(View view) {
        sName = findViewById(R.id.serviceName);
        String inputService = sName.getText().toString();
        if(inputService.isEmpty()){
            sName.setError("Please enter a name of service");
        }
        for(ServiceClass service:serviceList){
            if(service.getServiceName().equals(inputService)){
                deleteService(inputService);
                deleteBranchServices(inputService);
                return;
            }
        }

        Toast.makeText(this,"Service doesn't exist",Toast.LENGTH_SHORT).show();
        sName.setText("");
    }

    private void deleteService(String service){
        //note: change depending on how requirements are stored in firebase
        DatabaseReference dService = FirebaseDatabase.getInstance().getReference("Services").child(service);
        dService.removeValue();
        Toast.makeText(this,"Service deleted",Toast.LENGTH_SHORT).show();
        sName.setText("");
    }

    private void deleteBranchServices(String service){
        DatabaseReference dService = FirebaseDatabase.getInstance().getReference("EmService");
        dService.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren()){
                    for(DataSnapshot subItem: item.getChildren())
                        if(subItem.getKey().equals(service)){
                            subItem.getRef().removeValue();
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void onStart() {
        super.onStart();
        serviceList=new ArrayList<ServiceClass>();
        FirebaseDatabase databaseEM = FirebaseDatabase.getInstance();
        DatabaseReference dataRefEM= databaseEM.getReference().child("Services");
        dataRefEM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    ServiceClass newService=postSnapshot.getValue(ServiceClass.class);
                    serviceList.add(newService);
                }

                ServiceList servicesAdapter=new ServiceList(DeleteService.this,serviceList);

                listviewServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void backBnt(View view){
        Intent intent = new Intent(this, ServicePage.class);
        startActivity(intent);
    }
}