package com.example.rescuenow_dev.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserSettingsActivity extends AppCompatActivity {


    EditText mName, mAge;
    Button mSave;
    TextView mEmail, mGender,mBack;
    private String userId, userName, userAge, userImageUrl, userGender, userEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        mName = findViewById(R.id.username);

        mAge = findViewById(R.id.userage);

        mEmail = findViewById(R.id.useremail);

        mGender = findViewById(R.id.userGender);

        mSave = findViewById(R.id.savechanges);

        mBack = findViewById(R.id.back);

        mAuth = FirebaseAuth.getInstance();
        String currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentId);

        getUserInfo();



        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
                Toast.makeText(UserSettingsActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });
    }

    private void saveUserInformation() {
        userName = mName.getText().toString();
        userEmail = mEmail.getText().toString();
        userAge = mAge.getText().toString();
        userGender = mGender.getText().toString();

        Map userInfo = new HashMap();

        userInfo.put("name", userName);
        userInfo.put("age", userAge);

        mUserDatabase.updateChildren(userInfo);


    }

    private void getUserInfo() {

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if (map.get("name") != null) {
                        userName = map.get("name").toString();
                        mName.setText(userName);
                    }

                    if (map.get("age") != null) {
                        userAge = map.get("age").toString();
                        mAge.setText(userAge);
                    }

                    if (map.get("email") != null) {
                        userEmail = map.get("email").toString();
                        mEmail.setText(userEmail);
                    }

                    if (map.get("gender") != null) {
                        userGender = map.get("gender").toString();
                        mGender.setText(userGender);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}