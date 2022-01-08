package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayBranchesRating extends AppCompatActivity {
    ArrayList<Branch> branchList = new ArrayList<Branch>();
    ListView listView;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_branches_rating);
        listView = findViewById(R.id.LISTVIEW);
        Intent intent = getIntent();
        username = intent.getStringExtra("userAccount");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(DisplayBranchesRating.this, ShowRating.class);
                Branch branch = (Branch) listView.getItemAtPosition(i);
                intent1.putExtra("employeeAccount",branch.getEmployee());
                intent1.putExtra("userAccount", username);
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
                BranchList branchesAdapter=new BranchList(DisplayBranchesRating.this,branchList);
                listView.setAdapter(branchesAdapter);
                branchesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void DRbackBnt(View view){
        Intent intent = new Intent(this, CustomerPage.class);
        intent.putExtra("userName", username);
        finish();
        startActivity(intent);
    }
}