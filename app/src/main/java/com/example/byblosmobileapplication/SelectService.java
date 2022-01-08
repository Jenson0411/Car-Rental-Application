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

public class SelectService extends AppCompatActivity {
    EditText serviceName;
    public final static String EXTRA_TEXT = "com.example.byblosmobileapplication.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);
    }

    public void onClickFindService(View view){
        serviceName = findViewById(R.id.serviceName);

        String sName = serviceName.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef= database.getReference().child("Service");
        boolean isValidService =false;

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found =false;
                for(DataSnapshot item : snapshot.getChildren()) {
                    if (item.getKey().equals(sName)) {
                        found=true;
                        serviceFound();
                    }
                }
                Log.d("t", String.valueOf(found));
                if (!found)
                    serviceNotFound();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void serviceNotFound(){
        Toast.makeText(this.getApplicationContext(), "Service not found", Toast.LENGTH_LONG).show();
    }

    public void serviceFound(){
        Intent i = new Intent(this, EditService.class);
        EditText textView = (EditText) findViewById(R.id.serviceName);
        String serviceName=textView.getText().toString();
        i.putExtra(EXTRA_TEXT,serviceName);
        finish();
        startActivity(i);

    }
}