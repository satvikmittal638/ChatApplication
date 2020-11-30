package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextInputLayout logUname,logPwd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        logUname=findViewById(R.id.logUname);
        logPwd=findViewById(R.id.logPwd);
        mAuth=FirebaseAuth.getInstance();
    }

    public void userlogin(View view) {
    }

    public void toRegister(View view) {
        startActivity(new Intent(Login.this, Register.class));
        Toast.makeText(getApplicationContext(),"Register here",Toast.LENGTH_SHORT).show();
        finish();
    }
}