package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {
TextInputLayout regUname,regPwd;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
            regUname=findViewById(R.id.regUname);
            regPwd=findViewById(R.id.regPwd);
            mAuth=FirebaseAuth.getInstance();



    }

    public void toLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
        Toast.makeText(getApplicationContext(),"Login here",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void userRegister(View view) {
        String uname=regUname.getEditText().getText().toString(),
                pwd=regPwd.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(uname,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toasty.success(getApplicationContext(),"Registration Successful",Toasty.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,Login.class));
                            finish();
                        }else{
                            Toasty.error(getApplicationContext(),"Registration unsuccessful",Toasty.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(getApplicationContext(),e.getMessage(),Toasty.LENGTH_SHORT).show();
                    }
                });


    }
}