package com.example.rescuenow_dev.patient;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.diseases.SymptomsListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHomeFragment extends Fragment {

    private ImageView mSearch;
    private EditText mSymptomsEditText;
    private CardView mFirstAid, mFever, mHeart, mInjuries;
    TextView mUserName;
    DatabaseReference mUserDb;
    private FirebaseAuth mAuth;

    public PatientHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_patient_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mSearch = view.findViewById(R.id.search_btn);
        mSymptomsEditText = view.findViewById(R.id.search_field);
        mFirstAid = view.findViewById(R.id.card_basic_aid);
        mFever = view.findViewById(R.id.card_fever);
        mHeart = view.findViewById(R.id.card_heart);
        mInjuries = view.findViewById(R.id.card_injuries);
        mUserName = view.findViewById(R.id.user_name);

        setUserName();


        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(TextUtils.isEmpty(mSymptomsEditText.getText().toString())){
                   mSymptomsEditText.setError("Please enter your symptoms");
                }
                else {

                    String symptomsText = mSymptomsEditText.getText().toString();
                    Intent searchIntent = new Intent(getContext(), SymptomsListActivity.class);

                    searchIntent.putExtra("symptom_name",symptomsText);
                    startActivity(searchIntent);

                }

            }
        });

        mFirstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SymptomsListActivity.class);
                intent.putExtra("symptom_name","firstaid");
                startActivity(intent);

            }
        });

        mFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SymptomsListActivity.class);
                intent.putExtra("symptom_name","fever");
                startActivity(intent);

            }
        });

        mHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SymptomsListActivity.class);
                intent.putExtra("symptom_name","heart");
                startActivity(intent);

            }
        });

        mInjuries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SymptomsListActivity.class);
                intent.putExtra("symptom_name","injuries");
                startActivity(intent);

            }
        });

    }

    private void setUserName() {
        mUserDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    mUserName.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid().toString())
                            .child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
