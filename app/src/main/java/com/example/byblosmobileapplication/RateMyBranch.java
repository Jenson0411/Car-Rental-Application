package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class RateMyBranch extends AppCompatActivity {
    ArrayList<Branch> branchList = new ArrayList<Branch>();
    ListView listView;
    String username;
    private Spinner spinner;
    String rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_my_branch);

        listView = findViewById(R.id.listViewBranchesToRate);
        Intent intent = getIntent();
        username = intent.getStringExtra("userAccount");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
                Intent intent1 = new Intent(RateMyBranch.this, GiveRating.class);
                intent1.putExtra("userAccount", username);
                intent1.putExtra("employeeAccount", branchList.get(i).getEmployee());
                startActivity(intent1);
            }
        });


        FirebaseDatabase databaseBranches = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranches= databaseBranches.getReference().child("Branches");
        branchList=new ArrayList<Branch>();

        dataRefBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    Branch branch=postSnapshot.getValue(Branch.class);
                    branchList.add(branch);

                }
                BranchList branchesAdapter=new BranchList(RateMyBranch.this,branchList);
                listView.setAdapter(branchesAdapter);
                branchesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void RBbackBnt(View view){
        Intent intent = new Intent(this, CustomerPage.class);
        intent.putExtra("userName", username);
        finish();
        startActivity(intent);
    }
}