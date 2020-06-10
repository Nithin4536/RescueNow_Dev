package com.example.rescuenow_dev.patient.consult_doctors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;

import com.example.rescuenow_dev.R;

import java.util.List;

public class ConsultDoctorsAdapter extends RecyclerView.Adapter<ConsultDoctorsViewHolder> {

    private List<DoctorObject> doctorList;
    private Context context;

    public ConsultDoctorsAdapter(List<DoctorObject> matchesList, Context context){

        this.doctorList = matchesList;
        this.context = context;

    }

    @NonNull
    @Override
    public ConsultDoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_doctor_profile, null, false);

        RecyclerView.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutView.setLayoutParams(lp);

        ConsultDoctorsViewHolder rcv = new ConsultDoctorsViewHolder((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultDoctorsViewHolder holder, final int position) {


        holder.mDoctorId.setText(doctorList.get(position).getId());
        holder.mDoctorName.setText(doctorList.get(position).getName());
        holder.mDoctorEmail.setText(doctorList.get(position).getEmail());
        holder.mDoctorSpeciality.setText(doctorList.get(position).getSpeciality());
        holder.mDoctorHospital.setText(doctorList.get(position).getHospital_name());

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}
