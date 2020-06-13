package com.example.rescuenow_dev.patient.chat;

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
public class PatientInboxFragment extends Fragment {

    private RecyclerView mChatRecyclerView;
    private RecyclerView.Adapter mDoctorAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private String userId;

    public PatientInboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_inbox, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mChatRecyclerView = getView().findViewById(R.id.recyclerView);

        //allows to scroll freely through recycler view with no hickups
        mChatRecyclerView.setHasFixedSize(true);

        mChatLayoutManager = new LinearLayoutManager(getActivity());
        mChatRecyclerView.setLayoutManager(mChatLayoutManager);

        mDoctorAdapter = new ChatListViewAdapter(getDataSetMatches(), getActivity());
        mChatRecyclerView.setAdapter(mDoctorAdapter);
        mChatRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        getAvailableDoctors();
    }

    private void getAvailableDoctors() {

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                        GetDoctorInformation(userSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GetDoctorInformation(String key) {
        DatabaseReference doctorDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        doctorDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    Boolean role;
                    role = dataSnapshot.child("role").getValue().equals("Doctor");

                    if(role)
                    {
                        String hospital_Name;
                        String hospital_id;
                        String email;
                        String age;
                        String name;
                        String speciality;
                        String gender;

                        userId = dataSnapshot.getKey();

                        name = dataSnapshot.child("name").getValue().toString();
                        age = dataSnapshot.child("age").getValue().toString();
                        gender = dataSnapshot.child("gender").getValue().toString();
                        email = dataSnapshot.child("email").getValue().toString();
                        hospital_id = dataSnapshot.child("hospital_id").getValue().toString();
                        hospital_Name = dataSnapshot.child("hospital_name").getValue().toString();
                        speciality = dataSnapshot.child("speciality").getValue().toString();

                        DoctorObject obj = new DoctorObject(userId, name, gender, age, email,speciality, hospital_id, hospital_Name);
                        resultsDoctor.add(obj);
                        mDoctorAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private ArrayList<DoctorObject> resultsDoctor= new ArrayList<DoctorObject>();

    private List<DoctorObject> getDataSetMatches() {
        return resultsDoctor;
    }

}
