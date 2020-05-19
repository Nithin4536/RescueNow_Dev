package com.example.rescuenow_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

public class PatientDashboardActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    MaterialToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        //Firebase instance
        mAuth = FirebaseAuth.getInstance();


        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);




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
                Intent intent = new Intent(PatientDashboardActivity.this, LoginActivity.class);
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
