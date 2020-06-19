package com.example.rescuenow_dev.patient;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.diseases.Diseases;
import com.example.rescuenow_dev.doctor.ModifyDiseases;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllAvailableDiseases extends Fragment {

    RecyclerView mRecyclerView;

    FirebaseRecyclerOptions<Diseases> options;

    //Database reference
    DatabaseReference mDiseasesDatabase;

    String currentUserId;


    public AllAvailableDiseases() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_available_diseases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view_symtoms_patient);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDiseasesDatabase = FirebaseDatabase.getInstance().getReference().child("Diseases");
        mRecyclerView.setHasFixedSize(true);

        //Layout manager, grid, horizontal/vertical
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseSearch();
    }

    private void firebaseSearch() {
        options = new FirebaseRecyclerOptions.Builder<Diseases>().setQuery(mDiseasesDatabase, Diseases.class).build();

        FirebaseRecyclerAdapter<Diseases, PatientDiseaseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Diseases, PatientDiseaseViewHolder>(
                options
        ) {
            @Override
            protected void onBindViewHolder(@NonNull PatientDiseaseViewHolder viewHolder, int position, @NonNull final Diseases model) {
                viewHolder.setDetails(model.getName(), model.getDescription(), model.getPrecautions(), model.getSymptoms(), model.getMedicines());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(getContext(), ModifyDiseases.class);

                        intent.putExtra("disease_id",model.getId());
                        intent.putExtra("disease_name",model.getName());
                        intent.putExtra("disease_description",model.getDescription());
                        intent.putExtra("disease_precautions",model.getPrecautions());
                        intent.putExtra("disease_medicines", model.getMedicines());
                        intent.putExtra("disease_symptoms", model.getSymptoms());
                        intent.putExtra("disease_url", model.getUrl());

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public PatientDiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.symptoms_list_item, parent, false);

                return new PatientDiseaseViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}

class PatientDiseaseViewHolder extends  RecyclerView.ViewHolder {
    private TextView tv_name, tv_description, tv_symptoms, tv_medicines, tv_precautions;

    private View mView;
    PatientDiseaseViewHolder(@NonNull View itemView) {
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
