package com.example.rescuenow_dev.patient.chatmessages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.text.DateFormat.getDateTimeInstance;

public class ChatActivity extends AppCompatActivity {

    String doctorId;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private EditText mSendEditText;
    private TextView mDoctorName;
    private ImageButton mSendButton, mBackButton;
    int position;
    private String currentUserId, chatId;

    DatabaseReference mDatabaseUser,mDatabaseChat, mDatabaseDoctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        doctorId = getIntent().getExtras().getString("doctor_id");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("consult_connections").child(doctorId).child("chatId");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        mDatabaseDoctor = FirebaseDatabase.getInstance().getReference().child("Users").child(doctorId);

        mRecyclerView = findViewById(R.id.recyclerView);
        mBackButton = findViewById(R.id.back);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);

        mChatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);

        mChatAdapter = new ChatAdapter(getDataSetChat(), ChatActivity.this);

        mRecyclerView.setAdapter(mChatAdapter);

        mSendButton = findViewById(R.id.send);
        mSendEditText = findViewById(R.id.editTextMessage);
        mDoctorName = findViewById(R.id.matchName);

        getChatId();

        mDatabaseDoctor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String username;
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if (map.get("name") != null) {
                        username = map.get("name").toString();
                        mDoctorName.setText(username);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    private void sendMessage() {
        String sendMessageText = mSendEditText.getText().toString();

        if(!sendMessageText.isEmpty()){

            DatabaseReference newMessageDb = mDatabaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("senderId", currentUserId);
            newMessage.put("message", sendMessageText);
            newMessage.put("timeStamp", ServerValue.TIMESTAMP);

            newMessageDb.setValue(newMessage);
        }

        // to clear added text to null
        mSendEditText.setText(null);
        mRecyclerView.scrollToPosition(position);

    }

    private void getChatId() {
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    chatId = dataSnapshot.getValue().toString();
                    mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat").child(chatId);
                    //after getting chat id get the messages
                    getChatMessages();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    String message = null;
                    String createdByUser = null;
                    String timeStamp = null;

                    if(dataSnapshot.child("message").getValue()!= null){
                        message = dataSnapshot.child("message").getValue().toString();
                    }

                    if(dataSnapshot.child("senderId").getValue()!= null){
                        createdByUser = dataSnapshot.child("senderId").getValue().toString();

                    }

                    if(dataSnapshot.child("timeStamp").getValue()!= null){
                        timeStamp = getTimeDate(Long.parseLong(dataSnapshot.child("timeStamp").getValue().toString()));
                    }

                    if(message!=null && createdByUser !=null){
                        Boolean currentUserBoolean = false;

                        if(createdByUser.equals(currentUserId)){
                            currentUserBoolean = true;
                        }

                        ChatObject newMessage = new ChatObject(message, currentUserBoolean, timeStamp);
                        resultsChat.add(newMessage);
                        mChatAdapter.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }

    private ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>();

    private List<ChatObject> getDataSetChat() {
        return resultsChat;
    }

}