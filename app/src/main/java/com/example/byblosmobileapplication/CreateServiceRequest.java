package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class CreateServiceRequest extends AppCompatActivity {
    ArrayList<EditText> requiredInfo = new ArrayList<EditText>();
    ArrayList<String> branchRequiredInfo;
    String sName, eAccount, uAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service_request);
        Intent intent = getIntent();
        sName = intent.getStringExtra("serviceName");
        eAccount = intent.getStringExtra("employeeAccount");
        uAccount = intent.getStringExtra("userAccount");
        System.out.println(uAccount);
        TextView textView = findViewById(R.id.textServiceName);
        textView.setText(sName);
        addingEditText();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EmService").child(eAccount).child(sName).child("description");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchRequiredInfo = (ArrayList<String>) snapshot.getValue();
                for(int i = 0; i< branchRequiredInfo.size();i++){
                    requiredInfo.get(i).setVisibility(View.VISIBLE);
                    requiredInfo.get(i).setHint(branchRequiredInfo.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void addingEditText(){
        requiredInfo.add(findViewById(R.id.requireInfo1));
        requiredInfo.add(findViewById(R.id.requireInfo2));
        requiredInfo.add(findViewById(R.id.requireInfo3));
        requiredInfo.add(findViewById(R.id.requireInfo4));
        requiredInfo.add(findViewById(R.id.requireInfo5));
        requiredInfo.add(findViewById(R.id.requireInfo6));
        requiredInfo.add(findViewById(R.id.requireInfo7));
        requiredInfo.add(findViewById(R.id.requireInfo8));
        requiredInfo.add(findViewById(R.id.requireInfo9));
        requiredInfo.add(findViewById(R.id.requireInfo10));
        requiredInfo.add(findViewById(R.id.requireInfo11));
        requiredInfo.add(findViewById(R.id.requireInfo12));
        requiredInfo.add(findViewById(R.id.requireInfo13));
        requiredInfo.add(findViewById(R.id.requireInfo14));
    }

    public void onClickCreateRequests(View view){
        boolean flag = true;
        for(int i = 0; i< branchRequiredInfo.size();i++){
            if(branchRequiredInfo.get(i).equals("clientName") && !validateOnlyString(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Client's Name can't be empty or contains number");
            }

            else if(branchRequiredInfo.get(i).equals("carType") && !validateOnlyString(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Car type can't be empty or contain numbers");
            }

            else if(branchRequiredInfo.get(i).equals("purpose") && !validateOnlyString(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Purpose can't be empty or contain numbers");
            }

            else if(branchRequiredInfo.get(i).equals("maxNumKM") && !validateOnlyInt(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Invalid integer");
            }

            else if(branchRequiredInfo.get(i).equals("dateOfBirth") && !validateDateOfBirth(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Invalid date of birth; Format: mm/dd/yyyy");
            }
            else if(branchRequiredInfo.get(i).equals("email") && !validateEmail(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Invalid email");
            }
            else if(branchRequiredInfo.get(i).equals("driverLicense") && !validateDriverLisence(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Invalid driver license: Format: A1234-12345-12345");
            }
            else if(branchRequiredInfo.get(i).equals("pickUpTime") && !validateTime(requiredInfo.get(i).getText().toString())){
                flag = false;
                requiredInfo.get(i).setError("Invalid 24 format time; Format: hh:mm");
            }
            else if (branchRequiredInfo.get(i).equals("returnDate") && !validateDate(requiredInfo.get(i).getText().toString())) {
                flag =false;
                requiredInfo.get(i).setError("Date has to be later than today");
            }
            else if (branchRequiredInfo.get(i).equals("moveStartLocation") && !validateAddress(requiredInfo.get(i).getText().toString())) {
                flag =false;
                requiredInfo.get(i).setError("Invalid Address; Format: 123, Street");
            }
            else if (branchRequiredInfo.get(i).equals("moveEndLocation") && !validateAddress(requiredInfo.get(i).getText().toString())) {
                flag =false;
                requiredInfo.get(i).setError("Invalid Address; Format: 123, Street");
            }
            else if (branchRequiredInfo.get(i).equals("address") && !validateAddress(requiredInfo.get(i).getText().toString())) {
                flag =false;
                requiredInfo.get(i).setError("Invalid Address; Format: 123, Street");
            }

            else if (branchRequiredInfo.get(i).equals("numMovers") && !validateOnlyInt(requiredInfo.get(i).getText().toString())) {
                flag =false;
                requiredInfo.get(i).setError("Invalid integer");
            }
            else if (branchRequiredInfo.get(i).equals("numBoxes") && !validateOnlyInt(requiredInfo.get(i).getText().toString())) {
                flag =false;
                requiredInfo.get(i).setError("Invalid integer");
            }

        }

        if(flag){
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for(int i = 0; i<branchRequiredInfo.size(); i++){
                hashMap.put(branchRequiredInfo.get(i), requiredInfo.get(i).getText().toString());
            }
            hashMap.put("status", "");
            hashMap.put("userName", uAccount);
            hashMap.put("serviceName", sName);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Service Requests").child(eAccount);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() == null){
                        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
                        arrayList.add(hashMap);
                        databaseReference.setValue(arrayList);
                    }
                    else{
                        ArrayList<HashMap<String, String>> arrayList = (ArrayList<HashMap<String, String>>) snapshot.getValue();
                        arrayList.add(hashMap);
                        databaseReference.setValue(arrayList);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            finish();
            Intent intent = new Intent(this, CustomerPage.class);
            intent.putExtra("userName", uAccount);
            startActivity(intent);
        }


    }


    public boolean validateOnlyString(String str){
        Pattern valid_address_verification = Pattern.compile("^[a-z,A-Z]+$");
        Matcher matcher = valid_address_verification.matcher(str);
        boolean matches = matcher.find();
        return matches;
    }

    public boolean validateDateOfBirth(String date){
        if(date.isEmpty()){
            return false;
        }
        String [] mmDDYYYY = date.split("/");
        if(mmDDYYYY.length != 3){
            return false;
        }
        if(mmDDYYYY[2].length() !=4){
            return false;
        }
        if(mmDDYYYY[1].length() !=2 && mmDDYYYY[1].length() !=1){
            return false;
        }
        if(mmDDYYYY[0].length() !=2 && mmDDYYYY[0].length() !=1){
            return false;
        }
        try{
            int m = Integer.parseInt(mmDDYYYY[0]);
            int d = Integer.parseInt(mmDDYYYY[1]);
            int y = Integer.parseInt(mmDDYYYY[2]);
            System.out.println(m);
            if((1>m || m>12)){
                return false;
            }
            else if((1>d || d>31)){
                return false;
            }
            else if(1900>y || y>2021){
                return false;
            }
            else{
                return true;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean validateEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validateDriverLisence(String driverLisence){
        String patternString = "^[A-Z]+[0-9]{4}+(-){1}+[0-9]{5}+(-){1}+[0-9]{5}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(driverLisence);
        boolean matches = matcher.find();
        return matches;
    }

    public boolean validateTime(String time){
        if(time.isEmpty()){
            return false;
        }
        try{
            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1]);

            if(0>hour || hour >24){
                return false;
            }
            else if (0>minute || minute>60){
                return false;
            }
            else{
                return true;
            }
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean validateDate(String date){
        if(date.isEmpty()){
            return false;
        }
        String [] mmDDYYYY = date.split("/");
        if(mmDDYYYY.length != 3){
            return false;
        }
        if(mmDDYYYY[2].length() !=4){
            return false;
        }
        if(mmDDYYYY[1].length() !=2 && mmDDYYYY[1].length() !=1){
            return false;
        }
        if(mmDDYYYY[0].length() !=2 && mmDDYYYY[0].length() !=1){
            return false;
        }
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date currentDate = new Date();
            String[] currentDateM = formatter.format(currentDate).toString().split("/");

            int currentM = Integer.parseInt(currentDateM[0]);
            int currentD = Integer.parseInt(currentDateM[1]);
            int currentY = Integer.parseInt(currentDateM[2]);

            int m = Integer.parseInt(mmDDYYYY[0]);
            int d = Integer.parseInt(mmDDYYYY[1]);
            int y = Integer.parseInt(mmDDYYYY[2]);

            if(currentY<y){
                return true;
            }
            else if(currentY == y){
                if(currentM<m){
                    return true;
                }
                else if(currentM == m){
                    if(currentD<d){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }

        }
        catch(Exception e){
            return false;
        }
    }

    public boolean validateAddress(String address){
        Pattern valid_address_verification = Pattern.compile("^[0-9]+(,)+ +[A-Z]{1}+[a-z]+$");
        Matcher matcher = valid_address_verification.matcher(address);
        boolean matches = matcher.find();
        return matches;
    }

    public boolean validateOnlyInt(String integer){
        try{
            Integer.parseInt(integer);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}