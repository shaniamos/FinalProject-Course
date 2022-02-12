package com.example.myprocedure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        relativeLayout = findViewById(R.id.relative_layout_profile);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_personal_details);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "Your profile has been saved successfull", Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProfileActivity.this, "Enjoy our clinic app", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
}