package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BranchProfile extends AppCompatActivity {

    private DatabaseReference databaseServices;
    private EditText unitNumber, phoneNumber,streetName;
    private String userName;
    public static final Pattern valid_phone_verification = Pattern.compile("^\\d{9}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern valid_address_verification = Pattern.compile("^\\d{1,9}$",Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_profile);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

    }


    public void onClickCompleteBranch(View view){
        databaseServices= FirebaseDatabase.getInstance().getReference("Branches");
        unitNumber=findViewById(R.id.branchAddressField1);
        streetName=findViewById(R.id.branchAddressField2);
        phoneNumber=findViewById(R.id.branchNumberField);
        String sAddress = streetName.getText().toString();
        String sPhoneNum = phoneNumber.getText().toString();
        String sUnitNum=unitNumber.getText().toString();

        if(validation(sAddress,sPhoneNum,sUnitNum)){
            Branch branch=new Branch(userName,sPhoneNum,sUnitNum+" "+sAddress);

            databaseServices.child(userName).setValue(branch);
            Toast.makeText(this, "Completed Branch", Toast.LENGTH_SHORT).show();
            reset();

            Intent intent = new Intent(this, InitiateWorkingHours.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            finish();
        }
    }


    private void reset(){
        phoneNumber.setText("");
        unitNumber.setText("");
        streetName.setText("");
    }

    public boolean validation(String sAddress, String sPhoneNum,String sUnit){
        boolean isValid= true;
        if(sAddress.isEmpty()){
            streetName.setError("Please enter valid street name");
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
            unitNumber.setError("Unit number should be a number");
            reset();
            isValid=false;
        }

        return  isValid;
    }

}