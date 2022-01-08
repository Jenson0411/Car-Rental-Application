package com.example.byblosmobileapplication;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    //those two variables is to transfer data between activities
    public final static String EXTRA_NAME = "com.example.byblosmobileapplication.EXTRANAME";
    public final static String EXTRA_ROLE = "com.example.byblosmobileapplication.EXTRAROLE";

    //declare variables related to the EditText and Button
    private EditText username;
    private EditText password;

    private Button login;
    private Button signIn;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton(View view){
        //linked variables with components in UI
        username=findViewById(R.id.etName);
        password=findViewById(R.id.etPassword);

        login=findViewById(R.id.btnLogin);
        signIn=findViewById(R.id.btnSignUp);
        //define a variable to store the ID of the onclick button
        int pressID=view.getId();

        //get information from user
        String inputName=username.getText().toString();
        String inputPSW=password.getText().toString();

        //two cases on click buttons, one is login button, one is SignUp button
        switch (pressID){
            //if the on click button is login
            case R.id.btnLogin:
                //validate the input information
                boolean validInput = true;

                if(inputPSW.equals("")){
                    password.setError("Enter your password");
                    validInput=false;
                }
                if(inputName.equals("")){
                    username.setError("Enter your password");
                    validInput=false;

                }


                if(validInput){
                    //if information is valid, use login method
                    userLogin(inputPSW,inputName);
                    //Toast.makeText(this,"incorrect user name or password",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSignUp:
                //if the on click button is signUp, go to signUp page
                resetText();
                Intent intent = new Intent(this, SignUpPage.class);
                startActivity(intent);
        }

    }
    //method for users to login
    private void userLogin(String password, String userName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef= database.getReference().child("User List");

        dataRef.addValueEventListener(new ValueEventListener() {
            //boolean isValidAccount=false;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(flag) {
                    ArrayList<HashMap> arrayList = (ArrayList<HashMap>) snapshot.getValue();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).get("username").equals(userName) && arrayList.get(i).get("password").equals(password)) {

                            openWelcomePage(userName, arrayList.get(i).get("role").toString());
                            resetText();
                            return;
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void errorMSG(){
        Toast.makeText(this.getApplicationContext(),"incorrect user name or password",Toast.LENGTH_SHORT).show();
    }


    //method to reset to information to empty string
    private void resetText(){
        username.setText("");
        password.setText("");
    }

    private boolean isAdmin(String username, String passWord){
        return (username.equals("admin")&&passWord.equals("admin"));
    }

    private boolean isEmployee(String username,String passWord){
        return(username.equals("employee")&&passWord.equals("employee"));
    }

    //method to take users to Welcome page
    public  void openWelcomePage(String name, String role){
        flag = false;
        finish();
        Intent intent = new Intent(this, WelcomePage.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_ROLE,role);
        startActivity(intent);

    }


}