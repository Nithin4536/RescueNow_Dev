package com.example.rescuenow_dev.patient.chatmessages;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<ChatObject> chatList;
    private Context context;

    public ChatAdapter(List<ChatObject> chatList, Context context){
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolder rcv = new ChatViewHolder((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.mMessage.setText(chatList.get(position).getMessage());

        if(chatList.get(position).getCurrentUser()){

            holder.mMessage.setGravity(Gravity.END| Gravity.CENTER_VERTICAL);
            holder.mMessage.setTextColor(Color.parseColor("#ffffff"));
            holder.mContainer.setBackgroundResource(R.drawable.border_chat);
        }

        else
        {
            holder.mMessage.setGravity(Gravity.START| Gravity.CENTER_VERTICAL);
            holder.mContainer.setBackgroundResource(R.drawable.border_chat_otheruser);
        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
