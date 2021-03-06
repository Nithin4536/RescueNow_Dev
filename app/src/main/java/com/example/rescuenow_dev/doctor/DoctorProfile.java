package com.example.rescuenow_dev.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.chatmessages.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DoctorProfile extends AppCompatActivity {

    private String current_user,d_name, d_speciality, d_hospitalId, d_hospitalName, d_gender, d_age,d_email, d_studies;
    private TextView tvname, tvspeciality, tvemail, tvgender, tvage,tvhospitalname,tvhospitalid,tvStudies, mBack;
    Button btnContact;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private DatabaseReference mUserDatabase,mChatDatabase;
    String doctorId, chatId;
    String chat_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        mAuth = FirebaseAuth.getInstance();
        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        current_user = mAuth.getCurrentUser().getUid();

        bindUi();
        setDoctorData();
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultDoctor(doctorId);
            }
        });


    }

    private void goToChatRoom(String doctorId) {



    }

    private void consultDoctor(final String doctorId) {


        mChatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(current_user).child("consult_connections").child(doctorId).exists()
                &&!dataSnapshot.child(doctorId).child("consult_connections").child(current_user).exists()){

                     chat_key =  FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();

                    //Chat dashboard


                    //For Doctor
                    mChatDatabase.child(doctorId).child("consult_connections").child(current_user).child("chatId").setValue(chat_key);

                    //For Patient
                    mChatDatabase.child(current_user).child("consult_connections").child(doctorId).child("chatId").setValue(chat_key);

                }
                else
                {
                    chat_key= dataSnapshot.child(current_user).child("consult_connections").child(doctorId).child("chatId").getValue().toString();
                }

                Intent chatIntent = new Intent(DoctorProfile.this, ChatActivity.class);
                chatIntent.putExtra("doctor_id",doctorId);
                chatIntent.putExtra("chat_id", chat_key);
                startActivity(chatIntent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setDoctorData() {
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(doctorId);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if (map.get("name") != null) {
                        d_name = map.get("name").toString();
                        tvname.setText(d_name);
                    }

                    if (map.get("studies") != null) {
                        d_studies = map.get("studies").toString();
                        tvStudies.setText(d_studies);
                    }

                    if (map.get("email") != null) {
                        d_email = map.get("email").toString();
                        tvemail.setText(d_email);
                    }

                    if (map.get("age") != null) {
                        d_age = map.get("age").toString();
                        tvage.setText(d_age);
                    }

                    if (map.get("speciality") != null) {
                        d_speciality = map.get("speciality").toString();
                        tvspeciality.setText(d_speciality);
                    }

                    if (map.get("gender") != null) {
                        d_gender = map.get("gender").toString();
                        tvgender.setText(d_gender);
                    }

                    if (map.get("hospital_id") != null) {
                        d_hospitalId = map.get("hospital_id").toString();
                        tvhospitalid.setText(d_hospitalId);
                    }

                    if (map.get("hospital_name") != null) {
                        d_hospitalName = map.get("hospital_name").toString();
                        tvhospitalname.setText(d_hospitalName);
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void bindUi() {

        tvname = findViewById(R.id.tv_name);
        tvemail = findViewById(R.id.tv_email);
        tvgender = findViewById(R.id.tv_gender);
        tvspeciality = findViewById(R.id.tv_speciality);
        tvage = findViewById(R.id.tv_age);
        tvhospitalid = findViewById(R.id.tv_hospital_id);
        tvhospitalname = findViewById(R.id.tv_hospital_name);
        mBack = findViewById(R.id.back);
        btnContact = findViewById(R.id.con_doc);
        tvStudies = findViewById(R.id.tv_studies);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}