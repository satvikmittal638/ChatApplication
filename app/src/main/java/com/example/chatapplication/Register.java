package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatapplication.Model.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {
    TextInputLayout regEmail, regPwd;
    CircleImageView regPimage;
    private FirebaseAuth mAuth;
    private final int REQUEST_CODE=1;

    ProgressDialog pDialog;

    Uri pimageUri;
    Bitmap pimageBitmap;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, ActiveUsers.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Creating User");

        regEmail = findViewById(R.id.regEmail);
        regPwd = findViewById(R.id.regPwd);
        regPimage = findViewById(R.id.regPimage);
        mAuth = FirebaseAuth.getInstance();


        regPimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(Register.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Pick an image"),REQUEST_CODE);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toasty.warning(getApplicationContext(),"Permission Denied",Toasty.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                            }
                        }).check();


            }
        });

    }

    public void toLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
        Toast.makeText(getApplicationContext(), "Login here", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void userRegister(View view) {
        pDialog.show();
        String email = regEmail.getEditText().getText().toString(),
                pwd = regPwd.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            Toasty.success(getApplicationContext(), "Registration Successful", Toasty.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, ActiveUsers.class));
                            finish();

                            mAuth.signInWithEmailAndPassword(email, pwd)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                userModel u = new userModel(mAuth.getCurrentUser().getEmail(), "online");
                                                FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid())
                                                        .setValue(u);
                                            }
                                        }
                                    });

                            uploadImageToFirebase();

                        } else {
                            Toasty.error(getApplicationContext(), "Registration failed", Toasty.LENGTH_SHORT).show();
                            clearTexts();
                        }
                    }
                });


    }

    private void uploadImageToFirebase() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploadStorageReference = storage.getReference().child("pimages")
                .child(mAuth.getCurrentUser().getEmail());

        uploadStorageReference.putFile(pimageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    }
                });
    }

    private void clearTexts() {
        regEmail.getEditText().setText("");
        regPwd.getEditText().setText("");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE&& resultCode==RESULT_OK){
            assert data != null;
            pimageUri=data.getData();

            try {
                InputStream inputStream=getContentResolver().openInputStream(pimageUri);
                pimageBitmap= BitmapFactory.decodeStream(inputStream);
                regPimage.setImageBitmap(pimageBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
