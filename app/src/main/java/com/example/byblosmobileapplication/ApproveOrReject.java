package com.example.byblosmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApproveOrReject extends AppCompatActivity {
    TextView textView;
    String requestName;
    DatabaseReference myRefItems;
    String userName;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_or_reject);
        Intent intent = getIntent();
        requestName = intent.getStringExtra(ApproveRejectRequest.EXTRA_REQUEST);
        userName = intent.getStringExtra("userName");
        textView = findViewById(R.id.txtRequestName);
        textView.setText(requestName);
    }

    public void onClick(View view ){
        int pressID = view.getId();
        switch(pressID){
            case R.id.btnApprove:
                myRefItems = FirebaseDatabase.getInstance().getReference().child("Service Requests").child(userName).child(position).child("status");
                myRefItems.setValue("Approved");
                break;
            case R.id.btnReject:
                myRefItems = FirebaseDatabase.getInstance().getReference().child("Service Requests").child(userName).child(position).child("status");
                myRefItems.setValue("Reject");
                break;
        }
        finish();
        Intent intent = new Intent(this, ApproveRejectRequest.class);
        intent.putExtra("userName", userName);
        startActivity(intent);

    }


}