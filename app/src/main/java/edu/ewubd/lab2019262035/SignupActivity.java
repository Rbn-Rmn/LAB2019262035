package edu.ewubd.lab2019262035;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPhone, etUserId, etPW, etRPW;
    private CheckBox cbRemUserId, cbRemLogin;
    private Button btnLogin, btnGo, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decideNavigation();

        setContentView(R.layout.activity_signup);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etUserId = findViewById(R.id.etUserId);
        etPW = findViewById(R.id.etPW);
        etRPW = findViewById(R.id.etRPW);

        cbRemUserId = findViewById(R.id.cbRemUserId);
        cbRemLogin = findViewById(R.id.cbRemLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnGo = findViewById(R.id.btnGo);
        btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSignup();
            }
        });
    }

    private void processSignup(){
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String userId = etUserId.getText().toString().trim();
        String pass = etPW.getText().toString().trim();
        String rPass = etRPW.getText().toString().trim();
        String errorMsg = "";
        if(name.length() < 4 || name.length() > 10){
            errorMsg += "Invalid name,";
        }
//        if(validate email id){
//            errorMsg += "Invalid name,";
//        }
        // check other fields
        if(pass.length() != 4 || !pass.equals(rPass)){
            errorMsg += "Invalid pass,";
        }
        //////////////////////////////////////////////////
        if(!errorMsg.isEmpty()){
            // show error message here
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            return;
        }
        // if everything goes fine
        // store all data to shared preferences
        SharedPreferences sp = this.getSharedPreferences("my_info", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("USER_NAME", name);
        e.putString("USER_ID", userId);
        e.putString("EMAIL", email);
        e.putString("PHONE", phone);
        e.putString("PASSWORD", pass);
        e.putBoolean("REM_LOGIN", cbRemLogin.isChecked());
        e.putBoolean("REM_USER", cbRemUserId.isChecked());
        e.apply();
        //
        Intent i = new Intent(SignupActivity.this, MainActivity.class);
        i.putExtra("USER_ID", userId);
        startActivity(i);
        finish();
    }

    private void decideNavigation(){
        SharedPreferences sp = this.getSharedPreferences("my_info", MODE_PRIVATE);
        boolean isLogged = sp.getBoolean("REM_LOGIN", false);
        String userId = sp.getString("USER_ID", "NOT-CREATED");
        if(isLogged){
            Intent i = new Intent(SignupActivity.this, MainActivity.class);
            i.putExtra("USER_ID", userId);
            startActivity(i);
            finish();
        }
        if(!userId.equals("NOT-CREATED")){
            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
            i.putExtra("USER_ID", userId);
            startActivity(i);
            finish();
        }
    }
}