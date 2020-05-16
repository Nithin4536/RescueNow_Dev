package com.example.rescuenow_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private Button loginBtn;
    private EditText mUserEmail, mUserPassword;
    private DatabaseReference usersDb;
    String role, email, password;
    private TextView signup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkFirebaseAuth();
        initUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.addAuthStateListener(firebaseAuthStateListener);
                checkErrors();
            }
        });
    }

    private void checkErrors() {
      if(TextUtils.isEmpty(mUserEmail.getText().toString())){
            mUserEmail.setError("Enter your Email");
        }
        else if(TextUtils.isEmpty(mUserPassword.getText().toString()))
        {
            mUserPassword.setError("Enter your Password");
        }
        else if(mUserPassword.getText().toString().length()<6){
            mUserPassword.setError("Password length is minimum 6");
        }
        else
          {
              email = mUserEmail.getText().toString();
              password = mUserPassword.getText().toString();

              mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(!task.isSuccessful()){
                          Toast.makeText(LoginActivity.this, "Sign in Failed. Please try again.", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          Toast.makeText(LoginActivity.this, "Successfully signed in...", Toast.LENGTH_SHORT).show();
                          //userDashboard();
                      }
                  }
              });
          }
    }

    private void checkFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!= null){
                    String currentUserID = user.getUid();
                    usersDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                    usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            role = dataSnapshot.child("role").getValue().toString();
                            if(role.equals("doctor"))
                            {
                                Intent intent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else if(role.equals("patient"))
                            {
                                Intent intent = new Intent(LoginActivity.this, PatientDashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            Toast.makeText(LoginActivity.this, "Role: "+ role, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }

    private void initUI() {
        mUserEmail = findViewById(R.id.edit_text_login_email);
        mUserPassword = findViewById(R.id.edit_text_login_password);
        loginBtn = findViewById(R.id.button_login);
        signup = findViewById(R.id.text_view_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
