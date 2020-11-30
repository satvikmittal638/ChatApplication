package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {
    TextInputLayout logEmail,logPwd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logEmail=findViewById(R.id.logEmail);
        logPwd=findViewById(R.id.logPwd);
        mAuth=FirebaseAuth.getInstance();
    }

    public void userlogin(View view) {
        String email=logEmail.getEditText().getText().toString(),
                pwd=logPwd.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference().child("activeUsers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uemail").setValue(email);
                            Log.d("fdb","added "+email+" to active users");


                            Toasty.success(getApplicationContext(),"Login Successful",Toasty.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,ActiveUsers.class));
                            finish();
                        }else{
                            Toasty.error(getApplicationContext(),"Login failed",Toasty.LENGTH_SHORT).show();
                            clearTexts();
                        }
                    }
                });
    }

    public void toRegister(View view) {
        startActivity(new Intent(Login.this, Register.class));
        Toast.makeText(getApplicationContext(),"Register here",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void clearTexts(){
        logEmail.getEditText().setText("");
        logPwd.getEditText().setText("");
    }
}