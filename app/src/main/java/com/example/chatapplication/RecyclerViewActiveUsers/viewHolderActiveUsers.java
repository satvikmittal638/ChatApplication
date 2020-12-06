package com.example.chatapplication.RecyclerViewActiveUsers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class viewHolderActiveUsers extends RecyclerView.ViewHolder {
TextView disp_email,disp_status;
CardView sUserCardV;
CircleImageView activeUserImage;
    public viewHolderActiveUsers(@NonNull View itemView) {
        super(itemView);
        disp_email=itemView.findViewById(R.id.activeUserEmail);
        sUserCardV=itemView.findViewById(R.id.sUserCardV);
        disp_status=itemView.findViewById(R.id.userStatus);
        activeUserImage=itemView.findViewById(R.id.activeUserImage);
    }
}
