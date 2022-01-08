package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateService extends AppCompatActivity {
    CheckBox clientName, dateOfBirth,email, pickUpTime, driverLicense, returnDate,moveStartLocation, moveEndLocation, numMovers,carType ,purpose,maxNumKM, address,numBoxes;
    EditText serviceName, cost;
    ArrayList<String> description;
    DatabaseReference databaseServices;
    ArrayList<ServiceClass> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        description=new ArrayList<String>();

    }

    public void onClickCreateService(View view){

        databaseServices=FirebaseDatabase.getInstance().getReference("Services");

        clientName = findViewById(R.id.checkBox);
        dateOfBirth = findViewById(R.id.checkBox2);
        email = findViewById(R.id.checkBox3);
        driverLicense = findViewById(R.id.checkBox4);
        pickUpTime = findViewById(R.id.checkBox5);
        returnDate = findViewById(R.id.checkBox6);
        moveStartLocation = findViewById(R.id.checkBox7);
        moveEndLocation = findViewById(R.id.checkBox8);
        numMovers = findViewById(R.id.checkBox9);
        carType = findViewById(R.id.checkBox10);
        purpose = findViewById(R.id.checkBox11);
        maxNumKM = findViewById(R.id.checkBox12);
        address = findViewById(R.id.checkBox13);
        numBoxes = findViewById(R.id.checkBox14);
        serviceName = findViewById(R.id.serviceName);
        cost = findViewById(R.id.hourlyRate);
        String sName = serviceName.getText().toString();
        if(sName.isEmpty()){
            serviceName.setError("Please enter valid service name");
            reset();
            description.clear();
            return;
        }
        double hRate;
        try{
            hRate=Double.parseDouble(cost.getText().toString());
        }
        catch(Exception e){
            cost.setError("Please enter valid number");
            reset();
            description.clear();
            return;
        }

        if(clientName.isChecked()){
            description.add("clientName");
        }

        if(dateOfBirth.isChecked()){
            description.add("dateOfBirth");
        }

        if(email.isChecked()){
            description.add("email");
        }

        if(driverLicense.isChecked()){
            description.add("driverLicense");
        }

        if(pickUpTime.isChecked()){
            description.add("pickUpTime");
        }

        if(returnDate.isChecked()){
            description.add("returnDate");
        }

        if(moveStartLocation.isChecked()){
            description.add("moveStartLocation");
        }

        if(moveEndLocation.isChecked()){
            description.add("moveEndLocation");
        }

        if(numMovers.isChecked()){
            description.add("numMovers");
        }

        if(carType.isChecked()){
            description.add("carType");
        }

        if(purpose.isChecked()){
            description.add("purpose");
        }

        if(maxNumKM.isChecked()){
            description.add("maxNumKM");
        }

        if(address.isChecked()){
            description.add("address");
        }

        if(numBoxes.isChecked()){
            description.add("numBoxes");
        }
        if((!numBoxes.isChecked())&&(!address.isChecked())&&(!maxNumKM.isChecked())&&(!purpose.isChecked())&&(!carType.isChecked())&&(!numMovers.isChecked())&&(!moveEndLocation.isChecked())&&(!moveStartLocation.isChecked())&&(!returnDate.isChecked())&&(!pickUpTime.isChecked())&&(!driverLicense.isChecked())&&(!email.isChecked())&&(!dateOfBirth.isChecked())&&(!clientName.isChecked())){
            Toast.makeText(this,"At least check one required information",Toast.LENGTH_SHORT).show();
            return;
        }

        for(ServiceClass service:serviceList){
            if(service.getServiceName().equals(sName)){
                Toast.makeText(this,"Service has already existed",Toast.LENGTH_SHORT).show();
                reset();
                description.clear();
                return;
            }
        }
        ServiceClass newService=new ServiceClass(sName,hRate,description);

        databaseServices.child(sName).setValue(newService);
        Toast.makeText(this,"Service added",Toast.LENGTH_SHORT).show();
        description.clear();
        reset();

        Intent intent = new Intent(this, AdminPage.class);
        finish();
        startActivity(intent);
    }


    private void reset(){
        serviceName.setText("");
        cost.setText("");
        clientName.setChecked(false);
        dateOfBirth.setChecked(false);
        email.setChecked(false);
        driverLicense.setChecked(false);
        pickUpTime.setChecked(false);
        returnDate.setChecked(false);
        moveStartLocation.setChecked(false);
        moveEndLocation.setChecked(false);
        numMovers.setChecked(false);
        carType.setChecked(false);
        purpose.setChecked(false);
        maxNumKM.setChecked(false);
        address.setChecked(false);
        numBoxes.setChecked(false);
    }

    @Override
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CSbackBnt(View view){
        Intent intent = new Intent(this, ServicePage.class);
        finish();
        startActivity(intent);
    }
}