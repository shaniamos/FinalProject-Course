package com.example.myprocedure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        frameLayout= findViewById(R.id.container);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProcedureFragment()).commit();
        bottomNavigationView= findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(listener);

    }

    private NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.home_btn){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProcedureFragment()).commit();
            }else if(item.getItemId()==R.id.questions_btn){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new QuestionFragment()).commit();
            }else if(item.getItemId()==R.id.personal_details_btn){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
            }else if(item.getItemId()==R.id.contact_us_btn){
                AlertDialog.Builder descriptionPopup = new AlertDialog.Builder(FragmentActivity.this);
                View view = LayoutInflater.from(FragmentActivity.this).inflate(R.layout.contact_popup, null);
                ImageButton imageButtonEmailUs= view.findViewById(R.id.email_us);
                imageButtonEmailUs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"Clinic@MyProcedure.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT,"Hello, I'm interested in...");
                        intent.putExtra(Intent.EXTRA_TEXT,"Tell us, What service are you inquiring about?");
                        //intent.putExtra(Intent.EXTRA_,"Subject text");
                        startActivity(Intent.createChooser(intent,"Send a mail"));
                    }
                });
                ImageButton imageButtonCallUs= view.findViewById(R.id.call_us_button);
                imageButtonCallUs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     Intent intent = new Intent(Intent.ACTION_DIAL);
                     intent.setData(Uri.parse("tel:039887654"));
                     startActivity(intent);
                    }
                });
                descriptionPopup.setView(view);
                descriptionPopup.setCancelable(true);
                descriptionPopup.show();

            }else if (item.getItemId()==R.id.appointment_btn){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CalenderFragment()).commit();


            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}