package com.example.chatapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapplication.Model.disp_msgModel;
import com.example.chatapplication.RecyclerViewMessages.MessagesAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Chat_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private String userToChatWith;

    List<disp_msgModel> dispMsgModelList;

    TextInputLayout msg;
    ImageView send_btn;
    RecyclerView chatsRec;
    TextView userChattingHead;
    FirebaseUser currentUser;
    MessagesAdapter messagesAdapter;

    public Chat_Fragment() {

    }

    public Chat_Fragment(String userToChatWith) {
        this.userToChatWith=userToChatWith;
    }

    public static Chat_Fragment newInstance(String param1, String param2) {
        Chat_Fragment fragment = new Chat_Fragment();
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

        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        send_btn=view.findViewById(R.id.sendMsg_btn);
        msg=view.findViewById(R.id.msg);
        userChattingHead=view.findViewById(R.id.head);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        userChattingHead.setText(userToChatWith);

        chatsRec=view.findViewById(R.id.chatmsgRec);
        chatsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        chatsRec.setHasFixedSize(true);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=msg.getEditText().getText().toString();
                if(message.isEmpty())
                    Toasty.warning(view.getContext(),"You cannot send a empty message",Toasty.LENGTH_SHORT).show();
                else{
                    sendMsg(currentUser.getEmail(),userToChatWith,message);
                    msg.getEditText().setText("");
                    Toasty.success(view.getContext(),"Message sent successfully",Toasty.LENGTH_SHORT).show();
                }
            }
        });
        readMsg(currentUser.getEmail(),userToChatWith);
        return view;
    }

    //working fine
    private void sendMsg(String sender,String receiver,String msg) {
        Map<String,Object> mapObj=new HashMap<>();
        mapObj.put("sender",sender);
        mapObj.put("receiver",receiver);
        mapObj.put("msg",msg);

        FirebaseDatabase.getInstance().getReference().child("Chats").push().setValue(mapObj);
    }

    private void readMsg(String me, String him){
        dispMsgModelList=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Chats")
                .addValueEventListener(new ValueEventListener() {
                    //everytime data is added,it checks whether it ito be added to the List for displaying messages or not using a for each loop
                    @Override
                    public void onDataChange(@NonNull DataSnapshot parentSnapshot) {
                        dispMsgModelList.clear();
                        for (DataSnapshot childSnapshot:parentSnapshot.getChildren()){
                            disp_msgModel chatM=childSnapshot.getValue(disp_msgModel.class);
                            if(chatM.getSender().equals(me) && chatM.getReceiver().equals(him)||
                                    chatM.getSender().equals(him) && chatM.getReceiver().equals(me))
                            {
                                dispMsgModelList.add(chatM);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        messagesAdapter=new MessagesAdapter(dispMsgModelList);
        chatsRec.setAdapter(messagesAdapter);
    }
}