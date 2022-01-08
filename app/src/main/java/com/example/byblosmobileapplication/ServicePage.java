package com.example.byblosmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServicePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);
    }

    public void onClickAddService(View view){
        Intent intent = new Intent(this, CreateService.class);
        finish();
        startActivity(intent);
    }

    public void onClickEditService(View view){
        Intent intent = new Intent(this, EditService.class);

        startActivity(intent);
        finish();
    }
    public void onClickDisplayService(View view){
        Intent intent = new Intent(this, DisplayServices.class);
        finish();
        startActivity(intent);
    }

    public void onClickDeleteService(View view){
        Intent intent = new Intent(this, DeleteService.class);
        finish();
        startActivity(intent);
    }

    public void SPbackBnt(View view){
        Intent intent = new Intent(this, AdminPage.class);
        finish();
        startActivity(intent);
    }

}