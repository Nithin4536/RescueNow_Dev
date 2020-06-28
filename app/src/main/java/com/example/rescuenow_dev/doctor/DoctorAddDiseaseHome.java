package com.example.rescuenow_dev.doctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.diseases.DieseasesDetails;
import com.example.rescuenow_dev.diseases.Diseases;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


    EditText etDiseaseName, etDescription, etSymptoms, etPrecautions, etMedicines ,etUrl;
    String name, description, symptoms, precautions, medicines, url;
    DatabaseReference SymptomsDatabaseReference, DiseasesDatabaseReference, UserDatabaseReference;
    Button addDiseaseBtn;
    String currentUserId;
    Button mAddNewDisease, mViewAllDisease;
    LinearLayout btnLayout, addLayout, avaDiseasesLayout;
    RecyclerView mRecyclerView;
    TextView mUserName;
    DatabaseReference mUserDb;
    //Firebase Recycler options
    FirebaseRecyclerOptions<Diseases> options;

    //Database reference
    DatabaseReference mDiseasesDatabase;


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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SymptomsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Symptoms");
        DiseasesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Diseases");
        UserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        etDiseaseName = view.findViewById(R.id.disease_name);
        etDescription = view.findViewById(R.id.disease_description);
        etSymptoms = view.findViewById(R.id.disease_symptom);
        etPrecautions = view.findViewById(R.id.disease_precautions);
        etMedicines = view.findViewById(R.id.disease_medicines);
        etUrl = view.findViewById(R.id.disease_url);
        mRecyclerView = view.findViewById(R.id.recycler_view_symtoms);

        mUserName = view.findViewById(R.id.user_name);
        setUserName();

        btnLayout = view.findViewById(R.id.ll1);
        addLayout = view.findViewById(R.id.ll2);
        avaDiseasesLayout = view.findViewById(R.id.ll3);

        mAddNewDisease = view.findViewById(R.id.btn_add_disease);
        mViewAllDisease = view.findViewById(R.id.btn_view_diseases);


        mAddNewDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLayout.setVisibility(View.GONE);
                avaDiseasesLayout.setVisibility(View.GONE);
                addLayout.setVisibility(View.VISIBLE);
            }
        });

        mViewAllDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLayout.setVisibility(View.GONE);
                avaDiseasesLayout.setVisibility(View.VISIBLE);
                addLayout.setVisibility(View.GONE);
            }
        });

        //Add new disease
        addDiseaseBtn = view.findViewById(R.id.add_disease);

        addDiseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etDiseaseName.getText().toString())) {
                    etDiseaseName.setError("Enter Disease Name");
                } else if (TextUtils.isEmpty(etDescription.getText().toString())) {
                    etDescription.setError("Enter Disease Description");
                } else if (TextUtils.isEmpty(etSymptoms.getText().toString())) {
                    etSymptoms.setError("Enter Disease Symptoms");
                } else if (TextUtils.isEmpty(etPrecautions.getText().toString())) {
                    etPrecautions.setError("Enter Precautions");
                } else if (TextUtils.isEmpty(etMedicines.getText().toString())) {
                    etMedicines.setError("Enter Medicines");
                }
                else if (TextUtils.isEmpty(etUrl.getText().toString())) {
                    etUrl.setError("Enter Medicines");
                } else {

                    name = etDiseaseName.getText().toString();
                    description = etDescription.getText().toString();
                    symptoms = etSymptoms.getText().toString();
                    precautions = etPrecautions.getText().toString();
                    medicines = etMedicines.getText().toString();
                    url = etUrl.getText().toString();

                    final String key = FirebaseDatabase.getInstance().getReference().child("Diseases").push().getKey();

                    final Map symptomsData = new HashMap<>();

                    symptomsData.put("name", name);
                    symptomsData.put("description", description);
                    symptomsData.put("symptoms", symptoms);
                    symptomsData.put("medicines", medicines);
                    symptomsData.put("precautions", precautions);
                    symptomsData.put("id", key);
                    symptomsData.put("url", url);


                    DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("name");


                    userDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String doctorName;
                            doctorName = dataSnapshot.getValue().toString();

                            symptomsData.put("doctor", doctorName);

                            //Post to diseases collection
                            DiseasesDatabaseReference.child(key).updateChildren(symptomsData);

                            //post to symptoms collection
                            SymptomsDatabaseReference.child(symptoms).child(key).updateChildren(symptomsData);

                            //add diseases id to doctor collection
                            UserDatabaseReference.child(currentUserId).child("disease_postings").child(key).updateChildren(symptomsData);


                            Toast.makeText(getContext(), "Disease Information is added successfully", Toast.LENGTH_SHORT).show();
                            clearData();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getListofDiseases();

    }


        private void setUserName() {
            mUserDb = FirebaseDatabase.getInstance().getReference().child("Users");
            mUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        mUserName.setText(dataSnapshot.child(currentUserId)
                                .child("name").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    private void getListofDiseases() {

        options = new FirebaseRecyclerOptions.Builder<Diseases>().setQuery(DiseasesDatabaseReference, Diseases.class).build();

        FirebaseRecyclerAdapter<Diseases, DViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Diseases, DViewHolder>(
                options
        ) {
            @Override
            protected void onBindViewHolder(@NonNull DViewHolder viewHolder, int position, @NonNull final Diseases model) {
                viewHolder.setDetails(model.getName(), model.getDescription(), model.getPrecautions(), model.getSymptoms(), model.getMedicines());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(getContext(), DieseasesDetails.class);

                        intent.putExtra("disease_name", model.getName());
                        intent.putExtra("disease_description", model.getDescription());
                        intent.putExtra("disease_precautions", model.getPrecautions());
                        intent.putExtra("disease_medicines", model.getMedicines());
                        intent.putExtra("disease_symptoms", model.getSymptoms());
                        intent.putExtra("disease_url", model.getUrl());

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.symptoms_list_item, parent, false);

                return new DViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void clearData() {
        etDescription.setText("");
        etMedicines.setText("");
        etDiseaseName.setText("");
        etPrecautions.setText("");
        etUrl.setText("");
        etSymptoms.setText("");
    }

}


class DViewHolder extends  RecyclerView.ViewHolder {
    private TextView tv_name, tv_description, tv_symptoms, tv_medicines, tv_precautions;

    private View mView;
    DViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    void setDetails(String d_name, String d_desc, String d_precautions, String d_symptoms, String d_medicines){
        tv_name = mView.findViewById(R.id.text_view_disease_name);
        tv_description = mView.findViewById(R.id.text_view_disease_description);
        tv_symptoms = mView.findViewById(R.id.text_view_disease_symptoms);
        tv_medicines = mView.findViewById(R.id.text_view_disease_medicines);
        tv_precautions = mView.findViewById(R.id.text_view_disease_precautions);

        if(d_desc.length() > 150){
            d_desc = d_desc.substring(0, 150)+"....";
        }

        if(d_symptoms!=null){
            if(d_symptoms.length() > 100){
                d_symptoms = d_symptoms.substring(0, 100)+"...";
            }
        }

        tv_name.setText(d_name);
        tv_description.setText(d_desc);
        tv_symptoms.setText(d_symptoms);
        tv_precautions.setText(d_precautions);
        tv_medicines.setText(d_medicines);
    }

}
