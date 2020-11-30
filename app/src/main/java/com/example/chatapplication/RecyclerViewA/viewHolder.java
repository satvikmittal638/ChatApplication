package com.example.chatapplication.RecyclerViewA;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;

public class viewHolder extends RecyclerView.ViewHolder {
TextView disp_email;
    public viewHolder(@NonNull View itemView) {
        super(itemView);
        disp_email=itemView.findViewById(R.id.activeUserEmail);
    }
}
