package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileManager extends AppCompatActivity {
    DatabaseReference databaseServices;
    EditText address, phoneNumber,unit;
    private String userName;
    private Branch branch;
    String startTimeWeekday, startTimeWeekend, endTimeWeekday,endTimeWeekend;
    public static final Pattern valid_phone_verification = Pattern.compile("^\\d{9}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern valid_address_verification = Pattern.compile("^\\d{1,9}$",Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Branches/"+userName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    if(item.getKey().equals("endTimeWeekend")){
                        endTimeWeekend = item.getValue().toString();
                        System.out.println(endTimeWeekend);
                    }
                    else if(item.getKey().equals("startTimeWeekend")){
                        startTimeWeekend = item.getValue().toString();
                    }
                    else if(item.getKey().equals("endTimeWeekday")){
                        endTimeWeekday = item.getValue().toString();
                    }
                    else if(item.getKey().equals("startTimeWeekday")){
                        startTimeWeekday = item.getValue().toString();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onClickEdit(View view){
        databaseServices=FirebaseDatabase.getInstance().getReference("Branches");
        address = findViewById(R.id.streetNameTextView);
        unit=findViewById(R.id.unitTextView);
        phoneNumber = findViewById(R.id.phoneNumberTextView);

        String sUnit = unit.getText().toString();
        String sAddress = address.getText().toString();
        String sPhoneNum = phoneNumber.getText().toString();

        if (validation(sAddress,sPhoneNum,sUnit)) {

            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            reset();


            branch=new Branch(userName,sPhoneNum,sUnit+" "+sAddress);
            branch.setEndTimeWeekend(endTimeWeekend);
            branch.setEndTimeWeekday(endTimeWeekday);
            branch.setStartTimeWeekend(startTimeWeekend);
            branch.setStartTimeWeekday(startTimeWeekday);

            databaseServices.child(userName).setValue(branch);
            Toast.makeText(this,"Profile updated",Toast.LENGTH_SHORT).show();
            reset();

            finish();
            Intent intent = new Intent(this, BranchPage.class);
            intent.putExtra("userName", userName);
            startActivity(intent);

            //finish();
            //Intent intent = new Intent(this, EmployeePage.class);
            //intent.putExtra("userName", userName);
            //startActivity(intent);
        }


    }

    private void reset(){
        phoneNumber.setText("");
        address.setText("");
        unit.setText("");
    }

    public boolean validation(String sAddress, String sPhoneNum,String sUnit){
        boolean isValid= true;
        if(sAddress.isEmpty()){
            address.setError("Please enter valid street name");
            reset();
            isValid=false;
        }
        Matcher phoneMatcher = valid_phone_verification.matcher(sPhoneNum.trim());
        Matcher addressMatcher = valid_address_verification.matcher(sUnit.trim());

        if(sPhoneNum.isEmpty() || !phoneMatcher.find()){
            phoneNumber.setError("Phone Number should be a 9 digit number");
            reset();
            isValid=false;
        }
        if(sUnit.isEmpty() || !addressMatcher.find()){
            unit.setError("Unit number should be a number");
            reset();
            isValid=false;
        }

        return  isValid;
    }

    public void PmbackBnt(View view){
        Intent intent = new Intent(this, BranchPage.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}