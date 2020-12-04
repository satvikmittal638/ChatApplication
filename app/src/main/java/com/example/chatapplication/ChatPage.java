package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapplication.Model.disp_msgModel;
import com.example.chatapplication.RecyclerViewMessages.MessagesAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class ChatPage extends AppCompatActivity {
List<disp_msgModel> dispMsgModelList;

TextInputLayout msg;
ImageView send_btn;
RecyclerView chatsRec;
TextView userChattingHead;
FirebaseUser currentUser;
MessagesAdapter messagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        send_btn=findViewById(R.id.sendMsg_btn);
        msg=findViewById(R.id.msg);
        userChattingHead=findViewById(R.id.head);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        Intent intent=getIntent();
        String userToChatWith=intent.getStringExtra("userToChatWith");
        userChattingHead.setText(userToChatWith);

        chatsRec=findViewById(R.id.chatmsgRec);
        chatsRec.setLayoutManager(new LinearLayoutManager(this));
        chatsRec.setHasFixedSize(true);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=msg.getEditText().getText().toString();
                if(message.isEmpty())
                    Toasty.warning(getApplicationContext(),"You cannot send a empty message",Toasty.LENGTH_SHORT).show();
                else{
                    sendMsg(currentUser.getEmail(),userToChatWith,message);
                    msg.getEditText().setText("");
                    Toasty.success(getApplicationContext(),"Message sent successfully",Toasty.LENGTH_SHORT).show();
                    }
            }
        });
        readMsg(currentUser.getEmail(),userToChatWith);




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
                    public void onDataChange(@NonNull  DataSnapshot parentSnapshot) {
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
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
        messagesAdapter=new MessagesAdapter(dispMsgModelList);
        chatsRec.setAdapter(messagesAdapter);
    }
}