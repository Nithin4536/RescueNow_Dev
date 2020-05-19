package com.example.rescuenow_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private Button signupBtn;
    private EditText mUserName, mUserEmail, mUserPassword, mUserAge, mHospitalName, mHospitalId;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword, textInputLayoutName, textInputLayoutAge;
    private String name, email, password, age, gender, role, hospital_id, hospital_name;
    private Spinner mSpinner, mRoleSpinner;
    private AutoCompleteTextView mUserHospital;
    private ArrayAdapter<String> myGenderAdapter, mUserRoleAdapter;
    private MaterialToolbar materialToolbar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        checkFirebaseAuth();
        initUI();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkErrors();
            }
        });

    }

    private void checkFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

    }


    private void saveToDb() {
        isEmailExists(email);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String userId = mAuth.getCurrentUser().getUid();

                    Toast.makeText(SignupActivity.this, userId, Toast.LENGTH_SHORT).show();
                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    //To store the user information easily we use hashmaps
                    Map userInfo = new HashMap<>();

                    userInfo.put("name", name);
                    userInfo.put("email", email);
                    userInfo.put("gender", gender);
                    userInfo.put("age", age);
                    userInfo.put("role", role);

                    if(role.equals("Doctor"))
                    {
                        userInfo.put("hospital_id", hospital_id);
                        userInfo.put("hospital_name", hospital_name);
                        currentUserDb.updateChildren(userInfo);
                    }
                    else {
                        currentUserDb.updateChildren(userInfo);
                    }

                    Toast.makeText(SignupActivity.this, "Registration Successful. Please wait...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

    }


    private void initUI() {
        //Binding UI Elements
        materialToolbar = findViewById(R.id.topAppBar);
        signupBtn = findViewById(R.id.button_sign_up);
        mUserAge = findViewById(R.id.edit_text_signup_age);
        mUserEmail = findViewById(R.id.edit_text_signup_email);
        mUserPassword = findViewById(R.id.edit_text_signup_password);
        mUserName = findViewById(R.id.edit_text_signup_name);
        mUserPassword = findViewById(R.id.edit_text_signup_password);
        mSpinner = findViewById(R.id.spinner_signup_gender);
        mRoleSpinner = findViewById(R.id.spinner_signup_role);
        mHospitalName = findViewById(R.id.edit_text_hospital);
        mHospitalId = findViewById(R.id.edit_text_hospital_id);


        textInputLayoutAge = findViewById(R.id.input_layout_signup_age);
        textInputLayoutName = findViewById(R.id.input_layout_signup_name);
        textInputLayoutEmail = findViewById(R.id.input_layout_signup_email);
        textInputLayoutPassword = findViewById(R.id.input_layout_signup_password);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




        myGenderAdapter = new ArrayAdapter<>(SignupActivity.this,
                R.layout.spinner_gender, getResources().getStringArray(R.array.gender));

        mUserRoleAdapter = new ArrayAdapter<>(SignupActivity.this,
                R.layout.spinner_gender, getResources().getStringArray(R.array.role));

        myGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserRoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //allow the adapter to show the data inside the spinner.
        mSpinner.setAdapter(myGenderAdapter);
        mRoleSpinner.setAdapter(mUserRoleAdapter);

        mRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0)
                {
                    mHospitalName.setVisibility(View.GONE);
                    mHospitalId.setVisibility(View.GONE);
                }
                else {
                    mHospitalName.setVisibility(View.VISIBLE);
                    mHospitalId.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void checkErrors() {
        role =  mRoleSpinner.getSelectedItem().toString().trim();

        if(TextUtils.isEmpty(mUserName.getText().toString())){
            mUserName.setError("Enter your Name");
        }
        else if(TextUtils.isEmpty(mUserEmail.getText().toString())){
            mUserEmail.setError("Enter your Email");
        }
        //else if(isEmailValid(email)) {
          //  mUserEmail.setError("Enter valid Email");
        //}
        else if(TextUtils.isEmpty(mUserPassword.getText().toString()))
        {
            mUserPassword.setError("Enter your Password");
        }
        else if(mUserPassword.getText().toString().length()<6){
            mUserPassword.setError("Password length is minimum 6");
        }
        else if(TextUtils.isEmpty(mUserAge.getText().toString())){
            mUserAge.setError("Enter your Age");
        }
        else if ((Integer.parseInt(mUserAge.getText().toString()) <= 13 ) )
        {
            mUserAge.setError("Enter valid Age");
        }
        else if(role.equals("Patient")){
            //Check for errors
            name = mUserName.getText().toString();
            email = mUserEmail.getText().toString();
            password = mUserPassword.getText().toString();
            age = mUserAge.getText().toString();
            gender =  mSpinner.getSelectedItem().toString().trim();


            saveToDb();
        }
        else if(role.equals("Doctor"))
        {

            if(TextUtils.isEmpty(mHospitalName.getText().toString()))
            {
                mHospitalName.setError("Enter Hospital Name");
            }
            else if (TextUtils.isEmpty(mHospitalId.getText().toString()))
            {
                mHospitalId.setError("Enter your Hospital ID");
            }
            else {
                //Check for errors
                name = mUserName.getText().toString();
                email = mUserEmail.getText().toString();
                password = mUserPassword.getText().toString();
                age = mUserAge.getText().toString();
                gender =  mSpinner.getSelectedItem().toString().trim();
                hospital_id = mHospitalId.getText().toString();
                hospital_name = mHospitalName.getText().toString();

                saveToDb();
            }
        }
    }

    public void isEmailExists(String email){
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                        if(!check)
                        {
                            Log.d("Message","Email does not exist.");
                        }
                        else
                        {
                            textInputLayoutEmail.setError("Email already exists!");
                        }
                    }
                });
    }

    boolean isEmailValid(CharSequence email) {
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
