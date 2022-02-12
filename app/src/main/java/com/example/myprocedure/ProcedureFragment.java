package com.example.myprocedure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProcedureFragment extends Fragment {
    View view;
    Activity activity;
    RecyclerView recyclerView;
    ProcedureAdapter procedureAdapter;
    Button logOutBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        activity= getActivity();
        recyclerView= view.findViewById(R.id.procedure_recycler_view);
        logOutBtn = view.findViewById(R.id.logout_btn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        });
        ProcedureClass procedureClassPlasmaPen= new ProcedureClass(R.drawable.plasma_pen_treatment,"Plasma Pen", getResources().getString(R.string.plasma_pen_description),"https://youtu.be/Qaf0kRlti4E");
        ProcedureClass procedureClassMicroneedling= new ProcedureClass(R.drawable.microneedling_treatment,"Microneedling", getResources().getString(R.string.microneedling_description),"https://youtu.be/4YTecqmpfxU");
        ProcedureClass procedureClassPRP= new ProcedureClass(R.drawable.prp_treatment,"Platelet Rich Plasma", getResources().getString(R.string.prp_description),"https://youtu.be/mjA1cP1eXS0");
        ProcedureClass procedureClassBOTOX= new ProcedureClass(R.drawable.botox_treatment, "BOTOX Treatment", getResources().getString(R.string.botox_description), "https://youtu.be/oW4IGgoqRbY");
        ProcedureClass procedureClassIPL= new ProcedureClass(R.drawable.ipl_treatment, "Intense Pulsed Light", getResources().getString(R.string.ipl_description), "https://youtu.be/dm2Sq17kx3s");
        ArrayList<ProcedureClass> arrayList= new ArrayList<>();
        arrayList.add(procedureClassPlasmaPen);
        arrayList.add(procedureClassMicroneedling);
        arrayList.add(procedureClassPRP);
        arrayList.add(procedureClassBOTOX);
        arrayList.add(procedureClassIPL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        procedureAdapter = new ProcedureAdapter(arrayList, activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(procedureAdapter);








        return view;
    }
}
