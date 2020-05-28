package com.example.rescuenow_dev.diseases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;

public class DieseasesDetails extends AppCompatActivity {


    TextView diseaseTitle, diseaseDescription, diseaseSymptoms, diseasePrecautions, diseaseMedicines;
    String disease_title, disease_description, disease_symptoms, disease_precautions, disease_medicines;
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




        setUI();
    }

    private void setUI() {

        diseaseTitle.setText(disease_title);
        diseaseDescription.setText(disease_description);
        diseaseMedicines.setText(disease_medicines);
        diseaseSymptoms.setText(disease_symptoms);
        diseasePrecautions.setText(disease_precautions);

    }

    private void initUI() {

        diseaseDescription = findViewById(R.id.dis_description);
        diseaseTitle = findViewById(R.id.dis_name);
        diseaseSymptoms = findViewById(R.id.dis_symptoms);
        diseasePrecautions = findViewById(R.id.dis_precautions);
        diseaseMedicines = findViewById(R.id.dis_medicines);

    }
}
