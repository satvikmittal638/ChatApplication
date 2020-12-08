package com.example.chatapplication.RecyclerViewActiveUsers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.chatapplication.ChatPage;
import com.example.chatapplication.Model.userModel;
import com.example.chatapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

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



        StorageReference imageRef=FirebaseStorage.getInstance().getReference()
                .child("pimages").child(model.getEmail());
        try {
            File localFile=File.createTempFile(model.getEmail(),".jpeg");
            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.activeUserImage.setImageBitmap(bitmap);
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(context,e.getMessage(),Toasty.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


        FirebaseUser selfUser= FirebaseAuth.getInstance().getCurrentUser();
        if(selfUser!=null && (model.getEmail().equals(selfUser.getEmail())) ) {
            holder.disp_email.setText("You");
            holder.disp_status.setText("Online");
        }else{

            holder.disp_email.setText(model.getEmail());
            holder.disp_status.setText(model.getStatus());

            holder.sUserCardV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(holder.disp_email.getContext(), ChatPage.class);
                    intent.putExtra("userToChatWith",model.getEmail());
                    holder.disp_email.getContext().startActivity(intent);
//                    Toast.makeText(holder.disp_email.getContext(),"Chat here",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


}
