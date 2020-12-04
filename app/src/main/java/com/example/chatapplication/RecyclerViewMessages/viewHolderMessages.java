package com.example.chatapplication.RecyclerViewMessages;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;

public class viewHolderMessages extends RecyclerView.ViewHolder {
    TextView disp_msg,disp_sender;
    public viewHolderMessages(@NonNull View itemView) {
        super(itemView);

        disp_msg=itemView.findViewById(R.id.disp_msg);
        disp_sender=itemView.findViewById(R.id.disp_sender);
    }
}
