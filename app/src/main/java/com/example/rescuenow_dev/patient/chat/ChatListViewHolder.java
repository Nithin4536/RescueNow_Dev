package com.example.rescuenow_dev.patient.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.chatmessages.ChatActivity;

public class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mDoctorId, mDoctorName, mDoctorEmail, mDoctorHospital, mDoctorSpeciality;

    public TextView mMessage;
    public LinearLayout mContainer;


    public ChatListViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        mDoctorId = itemView.findViewById(R.id.doctor_id);
        mDoctorName = itemView.findViewById(R.id.doctor_name);
        mDoctorEmail = itemView.findViewById(R.id.doctor_email);
        mDoctorHospital = itemView.findViewById(R.id.doctor_hospital);
        mDoctorSpeciality = itemView.findViewById(R.id.doctor_speciality);
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("doctor_id",mDoctorId.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
