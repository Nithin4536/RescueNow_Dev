package com.example.rescuenow_dev.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rescuenow_dev.common.LoginActivity;
import com.example.rescuenow_dev.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorDashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    MaterialToolbar mToolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        //Firebase instance
        mAuth = FirebaseAuth.getInstance();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav_doctor);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch(menuItem.getItemId()){
                case R.id.homeFragment:
                    loadFragment(new DoctorAddDiseaseHome());
                    return true;
                case R.id.chatFragment:
                    loadFragment(new DoctorPatientChat());
                    return true;
                case R.id.remediesFragment:
                    loadFragment(new AvailableRemedies());
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
            case R.id.toolbar_signout: {
                mAuth.signOut();
                Intent intent = new Intent(DoctorDashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            case R.id.toolbar_profile: {
                Toast.makeText(this, "profile clicked", Toast.LENGTH_SHORT).show();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
