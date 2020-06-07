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