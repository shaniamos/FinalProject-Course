package com.example.myprocedure;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder>{
    ArrayList<AppointmentClass> appointmentClassArrayList = new ArrayList<>();
    Context context;
    String uid;
    DatabaseReference databaseReference;

    public AppointmentAdapter(ArrayList<AppointmentClass> appointmentClassArrayList, Context context, String uid) {
        this.appointmentClassArrayList = appointmentClassArrayList;
        this.context = context;
        this.uid = uid;
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("times");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView")int position) {
        Long timeEnd = appointmentClassArrayList.get(position).getTimeStart()+20*60*1000;
        String timeStartSt = getTime(appointmentClassArrayList.get(position).getTimeStart());
        String timeEndSt = getTime(timeEnd);
        holder.timeTextView.setText(timeStartSt+"-"+timeEndSt);
        if(appointmentClassArrayList.get(position).getAvailble()){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.back_pink));
            holder.timeTextView.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else if(appointmentClassArrayList.get(position).getUid().equals(uid)){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.pink_red));
            holder.timeTextView.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else{
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black));
            holder.timeTextView.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appointmentClassArrayList.get(position).getAvailble()){
                    AlertDialog.Builder attachAppointment = new AlertDialog.Builder(v.getRootView().getContext())
                            .setTitle("Do you want to schedule an appointment at this time?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setAppointment(appointmentClassArrayList.get(position).getNumber(), uid, false);
                                    holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.pink_red));
                                    holder.timeTextView.setTextColor(ContextCompat.getColor(context,R.color.white));
                                    appointmentClassArrayList.get(position).setAvailble(false);
                                    appointmentClassArrayList.get(position).setUid(uid);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    attachAppointment.show();
                }else if(appointmentClassArrayList.get(position).getUid().equals(uid)){

                    AlertDialog.Builder cancelAppointment = new AlertDialog.Builder(v.getRootView().getContext())
                            .setTitle("Cancel this appointment?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setAppointment(appointmentClassArrayList.get(position).getNumber(), "a", true);
                                    holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.back_pink));
                                    holder.timeTextView.setTextColor(ContextCompat.getColor(context,R.color.white));
                                    appointmentClassArrayList.get(position).setAvailble(true);
                                    appointmentClassArrayList.get(position).setUid("a");
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    cancelAppointment.show();

                }
            }
        });
    }
    private String getTime(Long time){
        String format = "kk:mm";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return appointmentClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView timeTextView;
        public ViewHolder (View view){
            super(view);
            this.timeTextView = view.findViewById(R.id.appointment_time);
            this.cardView= view.findViewById(R.id.appointment_card);
        }

    }
    private void setAppointment (int number, String newId, Boolean isAvailble){
        number = number-1;
        databaseReference.child(String.valueOf(number)).child("Uid").setValue(newId);
        databaseReference.child(String.valueOf(number)).child("isAvailble").setValue(isAvailble);
    }
}
