package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread bg = new Thread() {
            public void run() {
                try {
                    sleep(5000);

                    Intent i;

                    if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                         i=new Intent(getBaseContext(),ActiveUsers.class);
                    else
                         i=new Intent(getBaseContext(),Login.class);

                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    Toasty.error(getApplicationContext(),e.getMessage(),Toasty.LENGTH_SHORT).show();
                }
            }
        };

        bg.start();
    }
}