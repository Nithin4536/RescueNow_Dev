package com.example.rescuenow_dev.doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rescuenow_dev.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorAddDiseaseHome extends Fragment {


    EditText etDiseaseName, etDescription, etSymptoms, etPrecautions, etMedicines;
    String name, description, symtoms, precautions, medicines;

    Button addDiseaseBtn;

    public DoctorAddDiseaseHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_add_disease_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDiseaseName = view.findViewById(R.id.disease_name);
        etDescription = view.findViewById(R.id.disease_description);
        etSymptoms = view.findViewById(R.id.disease_symptom);
        etPrecautions = view.findViewById(R.id.disease_precautions);
        etMedicines = view.findViewById(R.id.disease_medicines);

        addDiseaseBtn = view.findViewById(R.id.add_disease);




        addDiseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etDiseaseName.getText().toString())) {
                    etDiseaseName.setError("Enter Disease Name");
                }
                else if (TextUtils.isEmpty(etDescription.getText().toString())) {
                    etDescription.setError("Enter Disease Description");
                }
                else if(TextUtils.isEmpty(etSymptoms.getText().toString()))
                {
                    etSymptoms.setError("Enter Disease Symptoms");
                }
                else if(TextUtils.isEmpty(etPrecautions.getText().toString()))
                {
                    etPrecautions.setError("Enter Precautions");
                }
                else if(TextUtils.isEmpty(etMedicines.getText().toString()))
                {
                    etMedicines.setError("Enter Medicines");
                }
                else
                {
                    
                }




            }
        });




    }
}
