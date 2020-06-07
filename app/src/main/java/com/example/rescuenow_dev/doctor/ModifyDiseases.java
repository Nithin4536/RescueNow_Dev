package com.example.rescuenow_dev.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ModifyDiseases extends YouTubeBaseActivity {

    EditText diseaseTitle, diseaseDescription, diseaseSymptoms, diseasePrecautions, diseaseMedicines;
    String disease_title,disease_id, disease_description, disease_symptoms, disease_precautions, disease_medicines, disease_url;
    private MaterialToolbar materialToolbar;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Button mSaveDisease;
    //Database reference
    DatabaseReference mSymptomsDatabase, mUsersDatabase, mDiseasesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_diseases);

        disease_id = getIntent().getStringExtra("disease_id");
        disease_title = getIntent().getStringExtra("disease_name");
        disease_description = getIntent().getStringExtra("disease_description");
        disease_medicines = getIntent().getStringExtra("disease_medicines");
        disease_symptoms = getIntent().getStringExtra("disease_symptoms");
        disease_precautions = getIntent().getStringExtra("disease_precautions");
        disease_url = getIntent().getStringExtra("disease_url");

        initUI();
        setUI();

    }


    private void setUI() {


        diseaseTitle.setText(disease_title);
        diseaseDescription.setText(disease_description);
        diseaseMedicines.setText(disease_medicines);
        diseaseSymptoms.setText(disease_symptoms);
        diseasePrecautions.setText(disease_precautions);


        materialToolbar.setTitle(disease_title.toUpperCase());
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(disease_url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize("AIzaSyCaXOyzL94451u0lXMJP3-2V39q0fJ-gzM", onInitializedListener);
            }
        });

        mSaveDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });
    }

    private void saveToDatabase() {

        mSymptomsDatabase = FirebaseDatabase.getInstance().getReference().child("Symptoms").child(disease_symptoms).child(disease_id);
        mDiseasesDatabase = FirebaseDatabase.getInstance().getReference().child("Diseases").child(disease_id);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        .child("disease_postings").child(disease_id);

        //Get data from Edit texts
        disease_medicines = diseaseMedicines.getText().toString();
        disease_symptoms= diseaseSymptoms.getText().toString();
        disease_precautions = diseasePrecautions.getText().toString();
        disease_title = diseaseTitle.getText().toString();
        disease_description = diseaseDescription.getText().toString();

        Map DiseasesMap = new HashMap();

        DiseasesMap.put("medicines", disease_medicines);
        DiseasesMap.put("precautions", disease_precautions);
        DiseasesMap.put("symptoms", disease_symptoms);
        DiseasesMap.put("description", disease_description);
        DiseasesMap.put("name", disease_title);

        mSymptomsDatabase.updateChildren(DiseasesMap);
        mDiseasesDatabase.updateChildren(DiseasesMap);
        mUsersDatabase.updateChildren(DiseasesMap);

        Toast.makeText(this, "Disease details are updated successfully!", Toast.LENGTH_SHORT).show();

        Intent homeIntent = new Intent(this, DoctorDashboardActivity.class);
        startActivity(homeIntent);
    }

    private void initUI() {

        youTubePlayerView = findViewById(R.id.dis_video);
        diseaseDescription = findViewById(R.id.dis_description);
        diseaseTitle = findViewById(R.id.dis_name);
        diseaseSymptoms = findViewById(R.id.dis_symptoms);
        diseasePrecautions = findViewById(R.id.dis_precautions);
        diseaseMedicines = findViewById(R.id.dis_medicines);
        materialToolbar = findViewById(R.id.toolbar);
        mSaveDisease = findViewById(R.id.save_disease);


    }
}