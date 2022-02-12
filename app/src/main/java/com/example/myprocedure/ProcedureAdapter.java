package com.example.myprocedure;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ProcedureAdapter extends RecyclerView.Adapter<ProcedureAdapter.ViewHolder> {

    ArrayList<ProcedureClass> procedureClassArrayList = new ArrayList<>();
    Context context;

    public ProcedureAdapter(ArrayList<ProcedureClass> procedureClassArrayList, Context context) {
        this.procedureClassArrayList = procedureClassArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.procedure, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.procedureText.setText(procedureClassArrayList.get(position).getProcedureText());
        holder.procedureDescription.setText(procedureClassArrayList.get(position).getDescription());

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.microneedling_treatment);
        Glide.with(context).load(procedureClassArrayList.get(position).getProcedureImage()).apply(requestOptions).into(holder.procedureImage);
        //holder.procedureImage.setImageResource(procedureClassArrayList.get(position).getProcedureImage());
        holder.procedureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder descriptionPopup = new AlertDialog.Builder(v.getRootView().getContext());
                View view = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.procedure_popup, null);
                TextView fullDescriptionNamePopUp = view.findViewById(R.id.procedure_description_name);
                TextView fullDescriptionPopup = view.findViewById(R.id.procedure_full_description);
                ImageView imageLinkDescriptionPopup = view.findViewById(R.id.procedure_description_link);
                fullDescriptionNamePopUp.setText(procedureClassArrayList.get(position).getProcedureText());
                fullDescriptionPopup.setText(procedureClassArrayList.get(position).getDescription());
                imageLinkDescriptionPopup.setImageResource(procedureClassArrayList.get(position).getProcedureImage());
                imageLinkDescriptionPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(procedureClassArrayList.get(position).getUrl()));
                        context.startActivity(intent);
                    }
                });
                descriptionPopup.setView(view);
                descriptionPopup.setCancelable(true);
                descriptionPopup.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return procedureClassArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView procedureImage;
        TextView procedureText;
        TextView procedureDescription;
        public ViewHolder(View view){
            super(view);
            procedureImage=view.findViewById(R.id.procedure_image);
            procedureText=view.findViewById(R.id.procedure_text);
            procedureDescription=view.findViewById(R.id.procedure_description);
        }
    }
}
