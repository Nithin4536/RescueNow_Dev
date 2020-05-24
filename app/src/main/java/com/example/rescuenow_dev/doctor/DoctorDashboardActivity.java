package com.example.rescuenow_dev.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rescuenow_dev.common.LoginActivity;
import com.example.rescuenow_dev.R;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorDashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button mSignout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        //Firebase instance
        mAuth = FirebaseAuth.getInstance();
        mSignout = findViewById(R.id.button_signout);

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(DoctorDashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
