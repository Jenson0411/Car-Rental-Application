package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InitiateWorkingHours extends AppCompatActivity {

    Branch branch;
    public String userName;
    public String address = "";
    public String phoneNumber = "";
    DatabaseReference ref;
    TextView textStartWeekdayHours, textStartWeekendHours, textEndWeekdayHours, textEndWeekendHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_working_hours);

        textStartWeekdayHours = findViewById(R.id.IwtextStartDay);
        textStartWeekendHours = findViewById(R.id.IwtextStartEnd);
        textEndWeekdayHours = findViewById(R.id.IwtextEndDay);
        textEndWeekendHours = findViewById(R.id.IwtextEndEnd);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        ref = FirebaseDatabase.getInstance().getReference("/Branches/"+userName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branch = new Branch(userName, address, phoneNumber);
                for(DataSnapshot item:snapshot.getChildren()){
                    if(item.getKey().equals("address")){
                        branch.setAddress(item.getValue().toString());
                    }
                    else if(item.getKey().equals("phoneNumber")){
                        branch.setPhoneNumber(item.getValue().toString());
                    }
                    else if(item.getKey().equals("endTimeWeekend")){
                        branch.setEndTimeWeekend(item.getValue().toString());
                    }
                    else if(item.getKey().equals("startTimeWeekend")){
                        branch.setStartTimeWeekend(item.getValue().toString());
                    }
                    else if(item.getKey().equals("endTimeWeekday")){
                        branch.setEndTimeWeekday(item.getValue().toString());
                    }
                    else if(item.getKey().equals("startTimeWeekday")){
                        branch.setStartTimeWeekday(item.getValue().toString());
                    }

                }

                System.out.println(branch.getStartTimeWeekend());
                textStartWeekendHours.setText(branch.getStartTimeWeekend());
                textStartWeekdayHours.setText(branch.getStartTimeWeekday());
                textEndWeekdayHours.setText(branch.getEndTimeWeekday());
                textEndWeekendHours.setText(branch.getEndTimeWeekend());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void msg(String str){
        Toast.makeText(this.getApplicationContext(),str , Toast.LENGTH_LONG).show();

    }

    public void popTimerPicker1(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                if(branch.getEndTimeWeekday().equals("")){
                    branch.setStartTimeWeekday(i+ ":" + i1);
                    ref.setValue(branch);
                    msg("Successfully edited Weekday Start Time");
                    return;
                }
                else{
                    String [] time = branch.getEndTimeWeekday().split(":");
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    if(hour>i){
                        branch.setStartTimeWeekday(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekday Start Time");
                    }
                    else if(hour == i && minute>i1){
                        branch.setStartTimeWeekday(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekday Start Time");
                    }
                    else{
                        String str = "Invalid Input. Start Time can't be later or the same as End Time ";
                        msg(str);
                    }
                }

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,0,0,true);
        timePickerDialog.setTitle("Select Start Time for Weekdays");
        timePickerDialog.show();
    }
    public void popTimerPicker2(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                if(branch.getStartTimeWeekday().equals("")){
                    branch.setEndTimeWeekday(i+ ":" + i1);
                    ref.setValue(branch);
                    msg("Successfully edited Weekday End Time");
                    return;
                }
                else{
                    String [] time = branch.getStartTimeWeekday().split(":");
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    if(hour<i){
                        branch.setEndTimeWeekday(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekday End Time");
                    }
                    else if(hour == i && minute<i1){
                        branch.setEndTimeWeekday(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekday End Time");
                    }
                    else{
                        String str = "Invalid Input. End Time can't be earlier or the same as Start Time ";
                        msg(str);
                    }
                }

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,0,0,true);
        timePickerDialog.setTitle("Select End Time for Weekdays");
        timePickerDialog.show();
    }
    public void popTimerPicker3(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                if(branch.getEndTimeWeekend().equals("")){
                    branch.setStartTimeWeekend(i+ ":" + i1);
                    ref.setValue(branch);
                    msg("Successfully edited Weekend Start Time");
                    return;
                }
                else{
                    String [] time = branch.getEndTimeWeekend().split(":");
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    if(hour>i){
                        branch.setStartTimeWeekend(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekend Start Time");
                    }
                    else if(hour == i && minute>i1){
                        branch.setStartTimeWeekend(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekend Start Time");
                    }
                    else{
                        String str = "Invalid Input. Start Time can't be later or the same as End Time ";
                        msg(str);
                    }
                }

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,0,0,true);
        timePickerDialog.setTitle("Select Start Time for Weekend");
        timePickerDialog.show();
    }
    public void popTimerPicker4(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if(branch.getStartTimeWeekend().equals("")){
                    branch.setEndTimeWeekend(i+ ":" + i1);
                    ref.setValue(branch);
                    msg("Successfully edited Weekend End Time");
                    return;
                }
                else{
                    String [] time = branch.getStartTimeWeekend().split(":");
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    if(hour<i){
                        branch.setEndTimeWeekend(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekend End Time");
                    }
                    else if(hour == i && minute<i1){
                        branch.setEndTimeWeekend(i+ ":" + i1);
                        ref.setValue(branch);
                        msg("Successfully edited Weekend End Time");
                    }
                    else{
                        String str = "Invalid Input. End Time can't be earlier or the same as Start Time ";
                        msg(str);
                    }
                }

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,0,0,true);
        timePickerDialog.setTitle("Select end Time for Weekend");
        timePickerDialog.show();
    }

    public void IwbackBnt(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}