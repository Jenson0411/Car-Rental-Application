package com.example.byblosmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }
    public void onClickManageAccount(View view){
        Intent intent = new Intent(this, ManageAccount.class);
        startActivity(intent);
    }
    public void onClickService(View view){
        Intent intent = new Intent(this, ServicePage.class);
        startActivity(intent);
    }

    public void onClickAmSignout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}