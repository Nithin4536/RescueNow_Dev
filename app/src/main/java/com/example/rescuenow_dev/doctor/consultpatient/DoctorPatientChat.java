package com.example.rescuenow_dev.doctor.consultpatient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.consult_doctors.ConsultDoctorsAdapter;
import com.example.rescuenow_dev.patient.consult_doctors.DoctorObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorPatientChat extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mPatientAdapter;
    private RecyclerView.LayoutManager mPatientLayout;
    private String userId;


    public DoctorPatientChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_patient_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = getView().findViewById(R.id.recyclerViewPatients);


        //allows to scroll freely through recycler view with no hickups
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);

        mPatientLayout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mPatientLayout);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        mPatientAdapter = new DoctorPatientAdapter(getDataSetMatches(), getActivity());
        mRecyclerView.setAdapter(mPatientAdapter);

        getConsultedPatients();
    }

    private void getConsultedPatients() {

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("consult_connections");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                        getPatientInformation(userSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getPatientInformation(String key) {

        DatabaseReference doctorDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        doctorDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                        String email;
                        String age;
                        String name;
                        String gender;

                        userId = dataSnapshot.getKey();

                        name = dataSnapshot.child("name").getValue().toString();
                        age = dataSnapshot.child("age").getValue().toString();
                        gender = dataSnapshot.child("gender").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();

                        PatientObject obj = new PatientObject(userId, name, gender, age, email);
                        resultsPatients.add(obj);
                        mPatientAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<PatientObject> resultsPatients= new ArrayList<PatientObject>();

    private List<PatientObject> getDataSetMatches() {
        return resultsPatients;
    }
}
