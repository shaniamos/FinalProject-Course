package com.example.myprocedure;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CalenderFragment extends Fragment {
    View view;
    Activity activity;
    CalendarView calendarView;
    RecyclerView recyclerView;
    ArrayList<AppointmentClass> appointmentClassArrayList = new ArrayList<>();
    ArrayList<AppointmentClass> appointmentClassArrayListPerDay= new ArrayList<>();
    boolean isReady = false;
    String Uid;
    AppointmentAdapter appointmentAdapter;
    private static final String TAG = " ";
    Long currentTime;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calender_fragment, container, false);
        activity= getActivity();
        calendarView = view.findViewById(R.id.calender);
        currentTime = calendarView.getDate();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendarStart = Calendar.getInstance();
                Calendar calendarEnd = Calendar.getInstance();
                calendarStart.set(year, month, dayOfMonth, 0, 0);
                calendarEnd.set(year,month,dayOfMonth, 23, 59);
                Long timeStart = calendarStart.getTimeInMillis();
                Long timeEnd = calendarEnd.getTimeInMillis();
                if(isReady) {
                    appointmentClassArrayListPerDay.clear();
                    for (AppointmentClass appointmentClass : appointmentClassArrayList) {
                        if (appointmentClass.getTimeStart() >= timeStart && appointmentClass.getTimeStart() < timeEnd &&
                                appointmentClass.getTimeStart()>=currentTime) {
                            appointmentClassArrayListPerDay.add(appointmentClass);
                        }
                    }
                    appointmentAdapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView= view.findViewById(R.id.appointments_recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Uid= firebaseUser.getUid();
        appointmentAdapter = new AppointmentAdapter(appointmentClassArrayListPerDay, activity, Uid);
        recyclerView.setAdapter(appointmentAdapter);
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("times");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentClassArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String uid = (String) dataSnapshot.child("Uid").getValue();
                    Log.d(TAG, "onDataChange: " + uid);
                    boolean isAvailble = (boolean) dataSnapshot.child("isAvailble").getValue();
                    Log.d(TAG, "onDataChange: " + isAvailble);
                    Long timeStart = (Long) dataSnapshot.child("timeStart").getValue();
                    Log.d(TAG, "onDataChange: " + timeStart);
                    Long number = (Long) dataSnapshot.child("number").getValue();
                    Log.d(TAG, "onDataChange: " + number);


                    AppointmentClass tempAppointmentClass = new AppointmentClass(timeStart, isAvailble, uid, Math.toIntExact(number));

                    Log.d(TAG, "onDataChange: " + tempAppointmentClass.toString());
                    appointmentClassArrayList.add(tempAppointmentClass);
                }
                isReady = true;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
