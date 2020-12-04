package com.example.chatapplication.RecyclerViewActiveUsers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;

public class viewHolderActiveUsers extends RecyclerView.ViewHolder {
TextView disp_email;
Button goToChat;
    public viewHolderActiveUsers(@NonNull View itemView) {
        super(itemView);
        disp_email=itemView.findViewById(R.id.activeUserEmail);
        goToChat=itemView.findViewById(R.id.goToChat);
    }
}
