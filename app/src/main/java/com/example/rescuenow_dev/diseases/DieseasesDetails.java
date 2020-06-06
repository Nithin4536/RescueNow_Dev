package com.example.rescuenow_dev.diseases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.PatientDashboardActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DieseasesDetails extends YouTubeBaseActivity {


    TextView diseaseTitle, diseaseDescription, diseaseSymptoms, diseasePrecautions, diseaseMedicines;
    String disease_title, disease_description, disease_symptoms, disease_precautions, disease_medicines, disease_url;
    private MaterialToolbar materialToolbar;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    //Database reference
    DatabaseReference mSymptomsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieseases_details);

        initUI();

        disease_title = getIntent().getStringExtra("disease_name");
        disease_description = getIntent().getStringExtra("disease_description");
        disease_medicines = getIntent().getStringExtra("disease_medicines");
        disease_symptoms = getIntent().getStringExtra("disease_symptoms");
        disease_precautions = getIntent().getStringExtra("disease_precautions");
        disease_url = getIntent().getStringExtra("disease_url");

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
    }

    private void initUI() {

        youTubePlayerView = findViewById(R.id.dis_video);
        diseaseDescription = findViewById(R.id.dis_description);
        diseaseTitle = findViewById(R.id.dis_name);
        diseaseSymptoms = findViewById(R.id.dis_symptoms);
        diseasePrecautions = findViewById(R.id.dis_precautions);
        diseaseMedicines = findViewById(R.id.dis_medicines);
        materialToolbar = findViewById(R.id.toolbar);




    }
}
