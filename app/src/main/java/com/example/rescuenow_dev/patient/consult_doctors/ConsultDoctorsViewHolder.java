package com.example.rescuenow_dev.patient.consult_doctors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.doctor.DoctorProfile;

public class ConsultDoctorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mDoctorId, mDoctorName, mDoctorEmail, mDoctorHospital, mDoctorSpeciality;
    Context context;

    public ConsultDoctorsViewHolder(@NonNull View itemView) {

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
        final Intent intent = new Intent(view.getContext(), DoctorProfile.class);
        Bundle b = new Bundle();
        b.putString("doctorId",mDoctorId.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);

    }


}
