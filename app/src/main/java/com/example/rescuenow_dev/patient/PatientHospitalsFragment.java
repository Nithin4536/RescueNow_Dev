package com.example.rescuenow_dev.patient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescuenow_dev.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHospitalsFragment extends Fragment {

    public PatientHospitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_hospitals, container, false);
    }
}