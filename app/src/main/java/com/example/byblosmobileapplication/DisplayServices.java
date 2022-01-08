package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayServices extends AppCompatActivity {
    ListView listView;
    ArrayList<ServiceClass> arrayList;
//    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_services);
        listView = findViewById(R.id.list);
//        ServiceList servicesAdapter=new ServiceList(DisplayServices.this,arrayList);
//        listView.setAdapter(servicesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ServiceClass newService= (ServiceClass) listView.getItemAtPosition(i);
                openNewActivity(newService.getServiceName());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayList=new ArrayList<ServiceClass>();
        listView = findViewById(R.id.list);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Services");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    ServiceClass newService=item.getValue(ServiceClass.class);
                    arrayList.add(newService);
                }
                ServiceList servicesAdapter=new ServiceList(DisplayServices.this,arrayList);
                listView.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void openNewActivity(String serviceName){
        Intent intent = new Intent(this, EditService.class);
        intent.putExtra("serviceName", serviceName);
        startActivity(intent);
    }

    public void DSbackBnt(View view){
        Intent intent = new Intent(this, ServicePage.class);
        finish();
        startActivity(intent);
    }


}