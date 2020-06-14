package com.example.rescuenow_dev.doctor.consultpatient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.consult_doctors.ConsultDoctorsViewHolder;
import com.example.rescuenow_dev.patient.consult_doctors.DoctorObject;

import java.util.List;


public class DoctorPatientAdapter extends RecyclerView.Adapter<DoctorPatientViewHolder>  {

    private List<PatientObject> patientList;
    private Context context;

    public DoctorPatientAdapter(List<PatientObject> matchesList, Context context){

        this.patientList = matchesList;
        this.context = context;

    }

    @NonNull
    @Override
    public DoctorPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_patient_profile, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        DoctorPatientViewHolder rcv = new DoctorPatientViewHolder((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorPatientViewHolder holder, int position) {

        holder.mPatientId.setText(patientList.get(position).getId());
        holder.mPatientName.setText(patientList.get(position).getName());
        holder.mPatientEmail.setText(patientList.get(position).getEmail());
        holder.mPatientAge.setText(patientList.get(position).getAge());
        holder.mPatientGender.setText(patientList.get(position).getGender());

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
}
