package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeePage extends AppCompatActivity {
    private String userName;
    TextView textView1;
    TextView textView2;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        textView1 = findViewById(R.id.textAddress);
        textView2 = findViewById(R.id.textPhone);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/" + userName + "/address");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView1.setText("Address: "+ snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("user/" + userName + "/phone");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView2.setText("Phone Number: "+ snapshot.getValue());
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
}