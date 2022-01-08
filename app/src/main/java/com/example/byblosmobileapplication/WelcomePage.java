package com.example.byblosmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomePage extends AppCompatActivity {

    EditText welcome, userRole;

    private String role;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Intent intent = getIntent();
        //gets name and role of current user
        userName = intent.getStringExtra(MainActivity.EXTRA_NAME);
        role = intent.getStringExtra(MainActivity.EXTRA_ROLE);
        userRole = findViewById(R.id.etDisplayRole);
        welcome=findViewById(R.id.etWelcome);
        //username=findViewById(R.id.etName);
        //updates welcome page to show name and role
        welcome.setText("Welcome " + userName );
        userRole.setText("You're a " +role);

    }

    public void onClickNext(View view){
        if(role.equals("Admin")) {
            finish();
            Intent intent = new Intent(this, AdminPage.class);
            startActivity(intent);
        }
        else if(role.equals("Employee")){
            finish();
            Intent intent =new Intent(this,BranchPage.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
        else if(role.equals("Customer")){
            finish();
            Intent intent =new Intent(this,CustomerPage.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
    }
}