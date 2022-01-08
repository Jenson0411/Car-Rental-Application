package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowRating extends AppCompatActivity {
    String userName, employeeAccount;
    ArrayList<String> ratings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rating);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userAccount");
        employeeAccount = intent.getStringExtra("employeeAccount");
        ListView listView = findViewById(R.id.listViewRating);
        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branch Rating").child(employeeAccount);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ratings);
        listView.setAdapter(arrayAdapter);
        dataRefBranches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){
                    ArrayList<HashMap> tmp = (ArrayList<HashMap>) snapshot.getValue();
                    HashMap hashMap;
                    Rating rating;
                    for(int i =0;i<tmp.size();i++){
                        hashMap= tmp.get(i);
                        rating = new Rating(hashMap.remove("rating").toString(),hashMap.remove("feedback").toString(), hashMap.remove("customerUserName").toString(), hashMap.remove("employeeUserName").toString());
                        ratings.add(rating.toString());
                    }


                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickGoCustomerPage(View view){
        finish();
        Intent intent = new Intent(this, DisplayBranchesRating.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}