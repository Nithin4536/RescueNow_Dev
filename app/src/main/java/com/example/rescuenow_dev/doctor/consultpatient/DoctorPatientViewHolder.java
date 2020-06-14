package com.example.rescuenow_dev.doctor.consultpatient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.doctor.DoctorProfile;
import com.example.rescuenow_dev.patient.chatmessages.ChatActivity;

public class DoctorPatientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView mPatientId, mPatientName, mPatientEmail, mPatientAge, mPatientGender;

    public DoctorPatientViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        mPatientAge = itemView.findViewById(R.id.patient_age);
        mPatientEmail = itemView.findViewById(R.id.patient_email);
        mPatientId = itemView.findViewById(R.id.patient_id);
        mPatientName = itemView.findViewById(R.id.patient_name);
        mPatientGender = itemView.findViewById(R.id.patient_gender);
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("doctor_id",mPatientId.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
