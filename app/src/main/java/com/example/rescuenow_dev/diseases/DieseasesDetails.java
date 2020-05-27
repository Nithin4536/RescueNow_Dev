package com.example.rescuenow_dev.diseases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.rescuenow_dev.R;

public class DieseasesDetails extends AppCompatActivity {


    TextView diseaseTitle, diseaseDescription, diseaseSymptoms, diseasePrecautions, diseaseMedicines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieseases_details);

        initUI();
    }

    private void initUI() {

        diseaseDescription = findViewById(R.id.dis_description);
        diseaseTitle = findViewById(R.id.dis_name);
        diseaseSymptoms = findViewById(R.id.dis_symptoms);
        diseasePrecautions = findViewById(R.id.dis_precautions);
        diseaseMedicines = findViewById(R.id.dis_medicines);


    }
}
