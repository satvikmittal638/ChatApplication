package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.chatapplication.Model.userModel;
import com.example.chatapplication.RecyclerViewActiveUsers.ActiveUsersAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class ActiveUsers extends AppCompatActivity {
    RecyclerView recyclerView;
   ActiveUsersAdapter firebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_users);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView=findViewById(R .id.activeUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        FirebaseRecyclerOptions<userModel> dataFromDB =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("activeUsers"), userModel.class)
                        .build();
        Log.d("fdb","got the data from the fdb");

      firebaseAdapter=new ActiveUsersAdapter(dataFromDB,this);

      recyclerView.setAdapter(firebaseAdapter);

    }



    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdapter.stopListening();
    }


    public void logout(View view) {
        FirebaseUser signOutUser=FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("activeUsers")
                .child(signOutUser.getUid()).removeValue();
        Log.d("fdb","removed "+signOutUser.getEmail()+" from active users");
        FirebaseAuth.getInstance().signOut();

        Toasty.success(getApplicationContext(),"Sign out Successful",Toasty.LENGTH_SHORT).show();
        startActivity(new Intent(this,Login.class));
        finish();
    }

}