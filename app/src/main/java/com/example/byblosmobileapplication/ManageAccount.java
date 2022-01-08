package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageAccount extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ArrayList<HashMap> tempList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        ListView listView = findViewById(R.id.userListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteAccount(listView.getItemAtPosition(i).toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User List");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                tempList = (ArrayList<HashMap>) snapshot.getValue();
                for(int i = 0; i< tempList.size(); i++){
                    arrayList.add(tempList.get(i).get("username").toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteAccount(String username){
        DatabaseReference dAccount = FirebaseDatabase.getInstance().getReference().child("User List");
        for(int i = 0; i< tempList.size(); i++){
            if(tempList.get(i).get("username").equals(username)){
                tempList.remove(i);
            }
        }
        dAccount.removeValue();
        DatabaseReference dAccount2 = FirebaseDatabase.getInstance().getReference().child("User List");
        dAccount2.setValue(tempList);

        Intent intent = new Intent(this, AdminPage.class);
        startActivity(intent);

        Toast.makeText(this.getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();
    }
}