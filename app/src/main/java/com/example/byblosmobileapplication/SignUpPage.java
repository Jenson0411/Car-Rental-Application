package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SignUpPage extends AppCompatActivity {
    //Instance variables
    // These are variable that the user enters in the sign up pages
    private EditText username;
    private EditText password;
    private EditText email;
    private  EditText confirmPassword;
    private Spinner spinner;
    private  String role = "";
    ArrayList<Account> arrayList;
    ArrayList<HashMap> accountList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        username=findViewById(R.id.etName2);
        password=findViewById(R.id.etPassword2);
        email=findViewById(R.id.etEmail2);
        confirmPassword= findViewById(R.id.etConfirmPassword);
        getAccountList();

        spinner=findViewById(R.id.etSpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("User List");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = (ArrayList<Account>)snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void onClickRegister(View view){

        // Finding the ID of each of the text that the user has entered and turn them into string, so that they can be stored in the firebase


        String inputName=username.getText().toString();
        String inputPSW=password.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPSW2 = confirmPassword.getText().toString();

        //Checking the validity of the information that the user has entered
        boolean emailValid = validEmail(inputEmail);
        boolean notEmpty = emptyFields(inputEmail,inputName,inputPSW,inputPSW2);
        boolean passwordMatch = matchPassword(inputPSW,inputPSW2);

        if(validateExistence(inputName)){
            Toast.makeText(this,"User has already existed",Toast.LENGTH_SHORT).show();
            resetText();
            return;
        }
        // Setting up the information that the user has entered into firebase, then leading them to the login page
        if(emailValid&& notEmpty&& passwordMatch){
            Account account;
            // User is a customer
            if(role.equals("Customer")) {
                account = new CustomerAccount(inputName, inputEmail, inputPSW, role);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("User List");
                arrayList.add(account);
                databaseReference1.setValue(arrayList);

                resetText();
                openLoginPage();
            }
            // User is an employee
            else if (role.equals("Employee")){
                System.out.println("hi");
                account = new EmployeeAccount(inputName, inputEmail, inputPSW, role);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("User List");
                arrayList.add(account);
                databaseReference1.setValue(arrayList);
                openBranchProfile();
                resetText();
            }
            else if (role.equals("Admin")){

                account = new AdminAccount(inputName, inputEmail, inputPSW, role);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("User List");
                arrayList.add(account);
                databaseReference1.setValue(arrayList);
                openLoginPage();
                resetText();
            }

        }


    }

    // This check the validity of the email that the user has entered. They can only enter gmail, hotmail, and outlook cmails
    public boolean validEmail(String email){

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else{
            this.email.setError("invalid email");
            return false;
        }
    }

    // Checks what infomation is still left blank
    private boolean emptyFields(String email, String name, String password, String password2){
        if(email.isEmpty()) {
            this.email.setError("Missing email");
        }
        if(name.isEmpty()) {
            this.username.setError("Missing username");
        }
        if(password.isEmpty()) {
            this.password.setError("Missing Password");
        }
        if(password2.isEmpty()) {
            this.confirmPassword.setError("Missing Password");
        }

        return (!email.isEmpty() && !name.isEmpty() && !password.isEmpty()&& !password2.isEmpty());

    }

    // See if both passwords matches
    public boolean matchPassword(String password, String password2){
        if(!password.equals(password2)){
            this.password.setError("Password doesn't match");
            this.confirmPassword.setError("Password doesn't match");
        }
        return password.equals(password2);
    }

    //Reset all of the textboxes
    private void resetText(){
        username.setText("");
        password.setText("");
        email.setText("");
        confirmPassword.setText("");

    }

    // Takes the user back to the login page
    private void openLoginPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openBranchProfile(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Service Request/"+username.getText().toString());
        Request request;
        Intent intent=new Intent(this,BranchProfile.class);
        intent.putExtra("userName", username.getText().toString());
        startActivity(intent);
        finish();
    }

    public void getAccountList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference().child("User List");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accountList = (ArrayList<HashMap>) snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean validateExistence(String user){
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).get("username").equals(user)) {
                return true;
            }
        }
        return false;
    }

}