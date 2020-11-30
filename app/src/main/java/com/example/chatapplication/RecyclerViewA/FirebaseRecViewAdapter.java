package com.example.chatapplication.RecyclerViewA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.chatapplication.Model.userModel;
import com.example.chatapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

import es.dmoral.toasty.Toasty;

public class FirebaseRecViewAdapter extends FirebaseRecyclerAdapter<userModel,viewHolder> {
Context context;
    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        Toasty.error(context, error.getMessage(),Toasty.LENGTH_SHORT).show();
    }

    public FirebaseRecViewAdapter(@NonNull FirebaseRecyclerOptions<userModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_active_user,parent,false);
        return new viewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull userModel model) {
        holder.disp_email.setText(model.getUemail());
    }


}
