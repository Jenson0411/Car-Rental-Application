package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class EditService extends AppCompatActivity {
    CheckBox clientName, dateOfBirth,email, pickUpTime, driverLicense, returnDate,moveStartLocation, moveEndLocation, numMovers,carType ,purpose,maxNumKM, address,numBoxes;
    EditText cost;
    TextView serviceName;
    ArrayList<String> description;
    DatabaseReference databaseServices;
    ArrayList<ServiceClass> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        Intent intent = getIntent();
        String sName = intent.getStringExtra("serviceName");
        serviceName = findViewById(R.id.textView16);
        serviceName.setText(sName);
        setUp();
        description=new ArrayList<String>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Services").child(sName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList arrayList = (ArrayList) snapshot.child("description").getValue();
                if(arrayList.contains("clientName")){
                    clientName.setChecked(true);
                }
                if(arrayList.contains("dateOfBirth")){
                    dateOfBirth.setChecked(true);
                }
                if(arrayList.contains("email")){
                    email.setChecked(true);
                }
                if(arrayList.contains("driverLicense")){
                    driverLicense.setChecked(true);
                }
                if(arrayList.contains("pickUpTime")){
                    pickUpTime.setChecked(true);
                }
                if(arrayList.contains("carType")){
                    carType.setChecked(true);
                }
                if(arrayList.contains("returnDate")){
                    returnDate.setChecked(true);
                }
                if(arrayList.contains("moveStartLocation")){
                    moveStartLocation.setChecked(true);
                }
                if(arrayList.contains("moveEndLocation")){
                    moveEndLocation.setChecked(true);
                }
                if(arrayList.contains("numMovers")){
                    numMovers.setChecked(true);
                }
                if(arrayList.contains("purpose")){
                    purpose.setChecked(true);
                }
                if(arrayList.contains("maxNumKM")){
                    maxNumKM.setChecked(true);
                }
                if(arrayList.contains("address")){
                    address.setChecked(true);
                }
                if(arrayList.contains("numBoxes")){
                    numBoxes.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUp(){
        cost = findViewById(R.id.editTextView2);
        clientName = findViewById(R.id.editCheckBox1);
        dateOfBirth = findViewById(R.id.editCheckBox2);
        email = findViewById(R.id.editCheckBox3);
        driverLicense = findViewById(R.id.editCheckBox4);
        pickUpTime = findViewById(R.id.editCheckBox5);
        returnDate = findViewById(R.id.editCheckBox6);
        moveStartLocation = findViewById(R.id.editCheckBox7);
        moveEndLocation = findViewById(R.id.editCheckBox8);
        numMovers = findViewById(R.id.editCheckBox9);
        carType = findViewById(R.id.editCheckBox10);
        purpose = findViewById(R.id.editCheckBox11);
        maxNumKM = findViewById(R.id.editCheckBox12);
        address = findViewById(R.id.editCheckBox13);
        numBoxes = findViewById(R.id.editCheckBox14);
    }

    public void onClickEditService(View view){

        databaseServices=FirebaseDatabase.getInstance().getReference("Services");
        String sName = serviceName.getText().toString();
        if(sName.isEmpty()){
            serviceName.setError("Please enter valid service name");
            description.clear();
            return;
        }
        double hRate;
        try{
            hRate=Double.parseDouble(cost.getText().toString());
        }
        catch(Exception e){
            cost.setError("Please enter valid number");
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
        else{
            // Editing Admin Services
            ServiceClass newService=new ServiceClass(sName,hRate,description);
            databaseServices.child(sName).setValue(newService);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmService");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap oddBranchService;
                    ServiceClass newBranchService;
                    for(DataSnapshot item: snapshot.getChildren()){
                        oddBranchService =  (HashMap)item.getValue();
                        if(oddBranchService.get(sName)!=null){

                            item.getRef().child(sName).setValue(newService);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Toast.makeText(this,"Service edited",Toast.LENGTH_SHORT).show();

            finish();
            Intent intent = new Intent(this, DisplayServices.class);
            startActivity(intent);
        }
    }

    public void ESbackBnt(View view){
        Intent intent = new Intent(this, ServicePage.class);
        finish();
        startActivity(intent);
    }
}