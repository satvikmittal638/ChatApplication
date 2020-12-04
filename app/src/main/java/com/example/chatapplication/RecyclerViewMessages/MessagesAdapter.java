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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class MessagesAdapter extends RecyclerView.Adapter<viewHolderMessages> {
    //variables for deciding the message type
public static final int MSG_TYPE_ME=1;
public static final int MSG_TYPE_HIM=0;

    List<disp_msgModel> usefulMsg;

    public MessagesAdapter(List<disp_msgModel> usefulMsg) {
        this.usefulMsg=usefulMsg;
    }

    @NonNull
    @Override
    public viewHolderMessages onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view;
        if(viewType==MSG_TYPE_ME)//returns him chat item after checking the viewtype from the method
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_me, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_him, parent, false);
        }
        return new viewHolderMessages(view);


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolderMessages holder, int position) {
        disp_msgModel s_msg=usefulMsg.get(position);
        holder.disp_msg.setText(s_msg.getMsg());
    }

    @Override
    public int getItemCount() {
        return usefulMsg.size();
    }


    @Override
    public int getItemViewType(int position) {
        FirebaseUser cUser= FirebaseAuth.getInstance().getCurrentUser();
        assert cUser != null;
        if(usefulMsg.get(position).getSender().equals(cUser.getEmail()))//if current user is the sender
          return MSG_TYPE_ME;
        else
          return MSG_TYPE_HIM;

    }
}


