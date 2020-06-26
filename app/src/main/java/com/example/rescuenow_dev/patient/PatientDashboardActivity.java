package com.example.rescuenow_dev.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.common.AccountFragment;
import com.example.rescuenow_dev.common.LoginActivity;
import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.chat.PatientInboxFragment;
import com.example.rescuenow_dev.patient.consult_doctors.ConsultDoctorsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientDashboardActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    MaterialToolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    Button mAllDiseases;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        //Firebase instance
        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav_patient);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //set home as main screen
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch(menuItem.getItemId()){
                case R.id.homeFragment:
                    loadFragment(new PatientHomeFragment());
                    return true;
                case R.id.chatFragment:
                    loadFragment(new PatientInboxFragment());
                    return true;
                case R.id.hospitalFragment:
                    loadFragment(new PatientHospitalsFragment());
                    return true;
                case R.id.consultFragment:
                    loadFragment(new ConsultDoctorsFragment());
                    return true;
                default:
                    return false;
            }
        }
    };

    //To load fragments when pressed in bottom navigation bar
    private void loadFragment(Fragment fragment) {

        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
       // transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_profile: {
                loadFragment(new AccountFragment());
            }
            break;
            case R.id.toolbar_diseases: {
                loadFragment(new AllAvailableDiseases());
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
