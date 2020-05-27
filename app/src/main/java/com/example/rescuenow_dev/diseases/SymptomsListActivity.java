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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

        mSymptomsDatabase = FirebaseDatabase.getInstance().getReference().child("Symptoms").child(mSymptomName);


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
                viewHolder.setDetails(model.getName(), model.getDescription(), model.getPrecautions(), model.getSymptoms(), model.getMedicines());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DieseasesDetails.class);

                        intent.putExtra("disease_name",model.getName());
                        intent.putExtra("disease_description",model.getDescription());
                        intent.putExtra("disease_precautions",model.getPrecautions());
                        intent.putExtra("disease_medicines", model.getMedicines());
                        intent.putExtra("disease_symptoms", model.getSymptoms());

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
    private TextView tv_name, tv_description, tv_symptoms, tv_medicines, tv_precautions;

    private View mView;
    DiseaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    void setDetails(String d_name, String d_desc, String d_precautions, String d_symptoms, String d_medicines){
        tv_name = mView.findViewById(R.id.text_view_disease_name);
        tv_description = mView.findViewById(R.id.text_view_disease_description);
        tv_symptoms = mView.findViewById(R.id.text_view_disease_symptoms);
        tv_medicines = mView.findViewById(R.id.text_view_disease_medicines);
        tv_precautions = mView.findViewById(R.id.text_view_disease_precautions);

        tv_name.setText(d_name);
        tv_description.setText(d_desc);
        tv_symptoms.setText(d_symptoms);
        tv_precautions.setText(d_precautions);
        tv_medicines.setText(d_medicines);

    }





}
