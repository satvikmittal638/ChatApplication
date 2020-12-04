package com.example.chatapplication.RecyclerViewMessages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Model.disp_msgModel;
import com.example.chatapplication.R;
import com.example.chatapplication.RecyclerViewActiveUsers.viewHolderActiveUsers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class MessagesAdapter extends RecyclerView.Adapter<viewHolderMessages> {

    List<disp_msgModel> usefulMsg;

    public MessagesAdapter(List<disp_msgModel> usefulMsg) {
        this.usefulMsg=usefulMsg;
    }


    public void clearList(){
        this.usefulMsg.clear();
    }
    @NonNull
    @Override
    public viewHolderMessages onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message,parent,false);
        return new viewHolderMessages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolderMessages holder, int position) {
        disp_msgModel s_msg=usefulMsg.get(position);
        holder.disp_sender.setText(s_msg.getSender());
        holder.disp_msg.setText(s_msg.getMsg());
    }

    @Override
    public int getItemCount() {
        return usefulMsg.size();
    }
}


