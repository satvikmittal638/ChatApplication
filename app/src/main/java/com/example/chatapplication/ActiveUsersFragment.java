package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.chatapplication.Model.userModel;
import com.example.chatapplication.RecyclerViewActiveUsers.ActiveUsersAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;


public class ActiveUsersFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ActiveUsersAdapter firebaseAdapter;
    Button logout;

    public ActiveUsersFragment() {
    }


    public static ActiveUsersFragment newInstance(String param1, String param2) {
        ActiveUsersFragment fragment = new ActiveUsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_active_users, container, false);
        recyclerView=view.findViewById(R .id.activeUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        logout=view.findViewById(R.id.logout);

        FirebaseRecyclerOptions<userModel> dataFromDB =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), userModel.class)
                        .build();
        Log.d("fdb","got the data from the fdb");

        firebaseAdapter=new ActiveUsersAdapter(dataFromDB,getContext());
        recyclerView.setAdapter(firebaseAdapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser signOutUser= FirebaseAuth.getInstance().getCurrentUser();

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(signOutUser.getUid()).child("status").setValue("offline");
                Log.d("fdb","made "+signOutUser.getEmail()+" offline in users");
                FirebaseAuth.getInstance().signOut();

                Toasty.success(getContext(),"Sign out Successful",Toasty.LENGTH_SHORT).show();

                startActivity(new Intent(view.getContext(),Login.class));
            }
        });



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        firebaseAdapter.stopListening();
    }

}