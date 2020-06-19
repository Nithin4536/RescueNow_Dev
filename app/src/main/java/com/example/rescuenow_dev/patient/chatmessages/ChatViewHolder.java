package com.example.rescuenow_dev.patient.chatmessages;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView mMessage;
    public LinearLayout mContainer;
    public TextView mtime;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        mMessage = itemView.findViewById(R.id.userMessage);
        mContainer = itemView.findViewById(R.id.container);
        mtime = itemView.findViewById(R.id.messageTime);
    }
}
