package com.example.myprocedure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment {
    View view;
    Activity activity;
    ImageButton profilePictureIB;
    FloatingActionButton profilePictureFab;
    EditText firstNameEt;
    EditText lastNameEt;
    EditText passwordEt;
    FloatingActionButton personalDetailsFab;
    String uid;
    String oldPictureUrl;
    ActivityResultLauncher <String> library;
    Uri uri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        activity= getActivity();
        profilePictureIB = view.findViewById(R.id.profile_picture);
        profilePictureFab = view.findViewById(R.id.fab_profile_picture);
        firstNameEt = view.findViewById(R.id.edit_first_name);
        lastNameEt = view.findViewById(R.id.edit_last_name);
        passwordEt = view.findViewById(R.id.edit_password);
        personalDetailsFab = view.findViewById(R.id.fab_personal_details);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getData();

        library = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null && result!=Uri.parse("")) {
                    uri = result;
                    uploadPicture(uid);
                }
            }
        });
        profilePictureFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                library.launch("image/*");
            }
        });
        personalDetailsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String password = passwordEt.getText().toString();
                user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            String firstName= firstNameEt.getText().toString();
                            String lastName= lastNameEt.getText().toString();
                            User updatedUser = new User(firstName,lastName,oldPictureUrl);
                            //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("user").child(uid);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(uid);

                            databaseReference.setValue(updatedUser);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle("You have to login again")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(activity, Login.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            builder.show();
                        }

                    }
                });
            }
        });




        return view;
    }
    private void getData(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                oldPictureUrl = user.getPicture_link();
                firstNameEt.setText(user.getFirst_name());
                lastNameEt.setText(user.getLast_name());
                RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.app_logo);
                Glide.with(activity).load(user.getPicture_link()).apply(requestOptions).into(profilePictureIB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void uploadPicture (String Uid){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(Uid);
        UploadTask uploadTask;
            uploadTask = storageReference.putFile(uri);

        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(task.isSuccessful()){
                    return storageReference.getDownloadUrl();
                }
                throw task.getException();
            }

        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    String Url = task.getResult().toString();
                    oldPictureUrl=Url;
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("picture_link");
                    databaseReference.setValue(Url);

                }
            }
        });
    }

}
