package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.chatapplication.Model.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {
TextInputLayout regEmail,regPwd;
private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(this,ActiveUsers.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


            regEmail=findViewById(R.id.regEmail);
            regPwd=findViewById(R.id.regPwd);
            mAuth=FirebaseAuth.getInstance();
    }

    public void toLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
        Toast.makeText(getApplicationContext(),"Login here",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void userRegister(View view) {
        String email=regEmail.getEditText().getText().toString(),
                pwd=regPwd.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            FirebaseUser newUser=mAuth.getCurrentUser();
                                    mAuth.signInWithEmailAndPassword(email,pwd)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    userModel u=new userModel(newUser.getEmail(),"online");

                                                    FirebaseDatabase.getInstance().getReference("Users").child(newUser.getUid())
                                                            .setValue(u);
                                                }
                                            });

                            Toasty.success(getApplicationContext(),"Registration Successful",Toasty.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,ActiveUsers.class));
                            finish();
                        }else{
                            Toasty.error(getApplicationContext(),"Registration failed",Toasty.LENGTH_SHORT).show();
                            clearTexts();
                        }
                    }
                });


    }

    private void clearTexts(){
        regEmail.getEditText().setText("");
        regPwd.getEditText().setText("");
    }
}