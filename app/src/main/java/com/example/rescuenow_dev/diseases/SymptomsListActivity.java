package com.example.rescuenow_dev.diseases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.common.LoginActivity;
import com.example.rescuenow_dev.common.SignupActivity;
import com.example.rescuenow_dev.patient.PatientDashboardActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SymptomsListActivity extends AppCompatActivity {

    String mSymptomName;

    //Firebase Auth
    FirebaseAuth mFirebaseAuth;

    //Firebase Recycler options
    FirebaseRecyclerOptions<Diseases> options;

    //Database reference
    DatabaseReference mSymptomsDatabase;

    //Current User
    FirebaseUser mFirebaseUser;
    Query mSearchQuery;

    RecyclerView mRecyclerview;
    String symptom_url;

    private MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_list);

        //Get the search extras data
        Bundle extras = getIntent().getExtras();

        if(extras == null) {
                mSymptomName= null;
        } else {
                mSymptomName= extras.getString("symptom_name");
        }


        //Getting current user id
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        materialToolbar = findViewById(R.id.sym_toolbar);

        mSymptomsDatabase = FirebaseDatabase.getInstance().getReference().child("Symptoms").child(mSymptomName);


        materialToolbar.setTitle(mSymptomName.toUpperCase());
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

       // mSearchQuery = mSymptomsDatabase.orderByChild("symptoms").startAt(mSymptomName).endAt(mSymptomName + "\uf8ff");

        mRecyclerview = findViewById(R.id.recycler_view_symtoms);

        //sets recycler view as fixed.
        mRecyclerview.setHasFixedSize(true);

        //Layout manager, grid, horizontal/vertical
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        firebaseSearch();
    }

    private void firebaseSearch() {
        options = new FirebaseRecyclerOptions.Builder<Diseases>().setQuery(mSymptomsDatabase, Diseases.class).build();

        FirebaseRecyclerAdapter<Diseases, DiseaseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Diseases, DiseaseViewHolder>(
               options
        ) {
            @Override
            protected void onBindViewHolder(@NonNull DiseaseViewHolder viewHolder, int position, @NonNull final Diseases model) {
                viewHolder.setDetails(model.getName(), model.getDescription(), model.getPrecautions(),
                        model.getSymptoms(), model.getMedicines(), model.getDoctor());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(getApplicationContext(), DieseasesDetails.class);

                        intent.putExtra("disease_name",model.getName());
                        intent.putExtra("disease_description",model.getDescription());
                        intent.putExtra("disease_precautions",model.getPrecautions());
                        intent.putExtra("disease_medicines", model.getMedicines());
                        intent.putExtra("disease_symptoms", model.getSymptoms());
                        intent.putExtra("disease_url", model.getUrl());
                        intent.putExtra("disease_doctor", model.getDoctor());

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.symptoms_list_item, parent, false);

                return new DiseaseViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }
}

class DiseaseViewHolder extends  RecyclerView.ViewHolder{
    private TextView tv_name, tv_description, tv_symptoms, tv_medicines, tv_precautions, tv_doctor;

    private View mView;
    DiseaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    void setDetails(String d_name, String d_desc, String d_precautions, String d_symptoms, String d_medicines, String d_doctor){
        tv_name = mView.findViewById(R.id.text_view_disease_name);
        tv_description = mView.findViewById(R.id.text_view_disease_description);
        tv_symptoms = mView.findViewById(R.id.text_view_disease_symptoms);
        tv_medicines = mView.findViewById(R.id.text_view_disease_medicines);
        tv_precautions = mView.findViewById(R.id.text_view_disease_precautions);
        tv_doctor = mView.findViewById(R.id.text_view_disease_doctor);

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
        tv_doctor.setText("Consult Doctor: "+d_doctor);
        tv_medicines.setText(d_medicines);
    }



}
