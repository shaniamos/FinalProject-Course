package com.example.myprocedure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button login_btn;
    EditText login_email;
    EditText login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn= findViewById(R.id.login_btn);
        login_email= findViewById(R.id.login_email_input);
        login_password = findViewById(R.id.login_password_input);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), FragmentActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Incorrect Email or Password. Type the correct user ID and password, and try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}