package com.example.byblosmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerPage extends AppCompatActivity {
    String accountName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);
        Intent intent = getIntent();
        accountName = intent.getStringExtra("userName");
    }

    public void onCLickButtonCreateRequest(View view){
        finish();
        Intent intent = new Intent(this, DisplayBranches.class);
        intent.putExtra("userAccount", accountName);
        startActivity(intent);
    }

    public void onClickButtonViewStatus(View view){
        finish();
        Intent intent = new Intent(this, ServiceRequestStatus.class);
        intent.putExtra("userAccount", accountName);
        startActivity(intent);
    }

    public void onClickButtonSearch(View view){
        finish();
        Intent intent = new Intent(this, SearchForBranch.class);
        intent.putExtra("userAccount", accountName);
        startActivity(intent);
    }

    public void onCLickButtonRate(View view){
        finish();
        Intent intent = new Intent(this, RateMyBranch.class);
        intent.putExtra("userAccount", accountName);
        startActivity(intent);
    }
    public void onClickViewRating(View view){
        finish();
        Intent intent = new Intent(this, DisplayBranchesRating.class);
        intent.putExtra("userAccount", accountName);
        startActivity(intent);
    }

    public void onClickCPSignout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}