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
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorAddDiseaseHome extends Fragment {


    EditText etDiseaseName, etDescription, etSymptoms, etPrecautions, etMedicines;
    String name, description, symptoms, precautions, medicines;
    DatabaseReference SymptomsDatabaseReference;
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
        SymptomsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Symptoms");


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

                    name = etDiseaseName.getText().toString();
                    description = etDescription.getText().toString();
                    symptoms = etSymptoms.getText().toString();
                    precautions = etPrecautions.getText().toString();
                    medicines = etMedicines.getText().toString();
                    final String key = SymptomsDatabaseReference.child(symptoms).push().getKey();

                    final Map symptomsData = new HashMap<>();

                    symptomsData.put("name", name);
                    symptomsData.put("description", description);
                    symptomsData.put("symptoms", symptoms);
                    symptomsData.put("medicines", medicines);
                    symptomsData.put("precautions", precautions);
                    symptomsData.put("url", "");

                    DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid()).child("name");


                    userDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String doctorName;
                            doctorName =  dataSnapshot.getValue().toString();
                            symptomsData.put("doctor", doctorName);

                            SymptomsDatabaseReference.child(symptoms).child(key).updateChildren(symptomsData).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(getContext(), "Disease Information is added successfully", Toast.LENGTH_SHORT).show();
                                    clearData();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Something went wrong check again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }
        });




    }

    private void clearData() {
        etDescription.setText("");
        etMedicines.setText("");
        etDiseaseName.setText("");
        etPrecautions.setText("");
        etSymptoms.setText("");
    }
}
