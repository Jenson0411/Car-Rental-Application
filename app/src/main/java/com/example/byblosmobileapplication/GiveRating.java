package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GiveRating extends AppCompatActivity {
    String userName ,employeeAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userAccount");
        employeeAccount = intent.getStringExtra("employeeAccount");
        TextView textView = findViewById(R.id.textEmployeeAccount);
        textView.setText("Rating for "+employeeAccount);
    }

    public void onClickRate(View view){
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText editText = findViewById(R.id.editTextFeedback);
        String rate = Integer.toString(Math.round(ratingBar.getRating()));
        String feedback = editText.getText().toString();
        Rating rating = new Rating(rate,feedback,userName,employeeAccount);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Branch Rating").child(employeeAccount);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList arrayList  = (ArrayList<Rating>)snapshot.getValue();
                if(arrayList == null){
                    arrayList = new ArrayList();
                    arrayList.add(rating);
                    databaseReference.setValue(arrayList);
                    finish();
                    Intent intent = new Intent(GiveRating.this, CustomerPage.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }
                else{
                    arrayList.add(rating);
                    databaseReference.setValue(arrayList);
                    finish();
                    Intent intent = new Intent(GiveRating.this, CustomerPage.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}