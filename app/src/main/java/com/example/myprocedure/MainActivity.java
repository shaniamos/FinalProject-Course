package com.example.myprocedure;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    EditText Email_input;
    EditText Password_input;
    EditText Firstname_input;
    EditText Lastname_input;
    Button Upload_btn;
    Button Signup_btn;
    Button Login_btn;
    Button Library_upload;
    FirebaseAuth Auth;
    ActivityResultLauncher activityResultLauncher;
    ActivityResultLauncher <String> library;
    Bitmap bitmap;
    FirebaseStorage firebaseStorage;
    Uri uri;
    int picture = 0;
    String firstName;
    String lastName;

    private static final String TAG = "Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email_input = findViewById(R.id.login_email_input);
        Password_input = findViewById(R.id.login_password_input);
        Firstname_input = findViewById(R.id.firstname_input);
        Lastname_input = findViewById(R.id.lastname_input);
        Upload_btn = findViewById(R.id.upload_btn);
        Signup_btn = findViewById(R.id.signup_btn);
        Login_btn = findViewById(R.id.login_btn);
        Library_upload = findViewById(R.id.library_upload);
        Auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        FirebaseUser firebaseUser = Auth.getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(getApplicationContext(), FragmentActivity.class);
            startActivity(intent);
        }

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null ){
                    Bundle bundle = result.getData().getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    picture = 1;
                }
            }
        });
        library = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null && result!=Uri.parse("")) {
                    uri = result;
                    picture = 2;
                }
            }
        });
        Library_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picture == 0)
                library.launch("image/*");
            }
        });
        Upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!= null && picture == 0){
                    activityResultLauncher.launch(intent);
                }
            }
        });
        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = Email_input.getText().toString();
                String Password = Password_input.getText().toString();
                firstName = Firstname_input.getText().toString();
                lastName = Lastname_input.getText().toString();
                if (!Email.isEmpty() && !Password.equals("") && !firstName.isEmpty() && !lastName.isEmpty()){
                    Auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser firebaseUser = Auth.getCurrentUser();
                                String Uid = firebaseUser.getUid();
                                if (picture != 0) {
                                    uploadPicture(Uid);
                                } else {
                                    User user = new User(firstName, lastName, "");
                                    uploadDb(user, Uid);
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "This email is already used", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }else
                    Toast.makeText(getApplicationContext(), "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadPicture (String Uid){
        StorageReference storageReference = firebaseStorage.getReference().child(Uid);
        UploadTask uploadTask;
        if(picture==1) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] matrix = byteArrayOutputStream.toByteArray();
            uploadTask = storageReference.putBytes(matrix);
        }else
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
                    User user = new User(firstName, lastName, Url);
                    Log.d(TAG, "onComplete: Upload successfully" + Url);
                    uploadDb(user, Uid);
                }
            }
        });
    }

    private void uploadDb(User user, String Uid){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("user").child(Uid);
        databaseReference.setValue(user);
        Intent intent= new Intent(getApplicationContext(), FragmentActivity.class);
        startActivity(intent);

    }

}