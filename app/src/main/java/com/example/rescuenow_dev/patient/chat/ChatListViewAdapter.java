package com.example.rescuenow_dev.patient.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.consult_doctors.DoctorObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

//this function takes care of populating data in xml layout
public class ChatListViewAdapter extends RecyclerView.Adapter<ChatListViewHolder> {

    private List<DoctorObject> doctorList;
    private Context context;

    public ChatListViewAdapter(List<DoctorObject> doctorList, Context context){

        this.doctorList = doctorList;
        this.context = context;

    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_doctor_profile, null, false);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatListViewHolder rcv = new ChatListViewHolder((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, final int position) {

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
