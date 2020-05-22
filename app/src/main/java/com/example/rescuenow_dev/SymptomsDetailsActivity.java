package com.example.rescuenow_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SymptomsDetailsActivity extends AppCompatActivity {

    String mSymptomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_details);

        Bundle extras = getIntent().getExtras();

        if(extras == null) {
                mSymptomName= null;
        } else {
                mSymptomName= extras.getString("symptom_name");
        }

        Toast.makeText(this, "Symtom Name: " + mSymptomName, Toast.LENGTH_SHORT).show();


    }
}
