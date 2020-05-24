package com.example.rescuenow_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rescuenow_dev.diseases.DiseaseAdapter;
import com.example.rescuenow_dev.diseases.Diseases;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SymptomsListActivity extends AppCompatActivity {

    String mSymptomName;

    //Firebase Auth
    FirebaseAuth mFirebaseAuth;

    //Database reference
    DatabaseReference mSymptomsDatabase;

    //Current User
    FirebaseUser mFirebaseUser;

    RecyclerView mRecyclerview;
    DiseaseAdapter adapter;

    List<Diseases> diseasesList;

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

       // Toast.makeText(this, "Symtom Name: " + mSymptomName, Toast.LENGTH_SHORT).show();

        //Getting current user id
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //init disease list
        diseasesList = new ArrayList<>();
        mRecyclerview = findViewById(R.id.recycler_view_symtoms);

        //sets recycler view as fixed.
        mRecyclerview.setHasFixedSize(true);

        //Layout manager, grid, horizontal/vertical
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        diseasesList.add(
                new Diseases(
                        "ANUG","Acute necrotizing ulcerative gingivitis (ANUG) is a common, non-contagious infection of the gums with sudden onset. The main features are painful, bleeding gums, and ulceration of inter-dental papillae (the sections of gum between adjacent teeth).", "Amoxicillin 500mg, 6 hourly for 5 days plus Metronidazole 400 mg 8 hours a day for five days",
                        "ulceration of gingival, fever, malaise, regional lymphadenitis, extensive destruction of face and jaws."));

     diseasesList.add(
                new Diseases(
                        "Dental Abscess","A dental abscess is a collection of pus that can form inside the teeth, in the gums or in the bone that holds the teeth in place. It's caused by a bacterial infection. An abscess at the end of a tooth is called a periapical abscess. An abscess in the gum is called a periodontal abscess.","fever, chills, throbbing pain of tooth, pain, swelling of the gingiva, sounding tissues, inability to open the mouth" ,
                        "irrigation and dressing repeated daily"));


        DiseaseAdapter adapter = new DiseaseAdapter(this, diseasesList);
        mRecyclerview.setAdapter(adapter);

    }
}
