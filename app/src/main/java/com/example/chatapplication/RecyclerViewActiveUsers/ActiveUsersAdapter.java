package com.example.chatapplication.RecyclerViewActiveUsers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chatapplication.ChatPage;
import com.example.chatapplication.Model.userModel;
import com.example.chatapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

import es.dmoral.toasty.Toasty;

public class ActiveUsersAdapter extends FirebaseRecyclerAdapter<userModel, viewHolderActiveUsers> {
Context context;
    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        Toasty.error(context, error.getMessage(),Toasty.LENGTH_SHORT).show();
    }

    public ActiveUsersAdapter(@NonNull FirebaseRecyclerOptions<userModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolderActiveUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_active_user,parent,false);
        return new viewHolderActiveUsers(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolderActiveUsers holder, int position, @NonNull userModel model) {
        holder.disp_email.setText(model.getEmail());
        holder.goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.disp_email.getContext(), ChatPage.class);
                intent.putExtra("userToChatWith",model.getEmail());
                holder.disp_email.getContext().startActivity(intent);
                Toast.makeText(holder.disp_email.getContext(),"Chat here",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
