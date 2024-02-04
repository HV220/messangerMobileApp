package com.example.messanger.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messanger.R;

public class ChatActivity extends AppCompatActivity {
    private TextView titleOwnerChat;
    private View statusView;
    private RecyclerView messagesRecycler;
    private EditText messageIconEditView;
    private ImageView action_image;

    public ChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
    }

    private void initViews() {
        titleOwnerChat = findViewById(R.id.titleOwnerChat);
        statusView = findViewById(R.id.statusView);
        messagesRecycler = findViewById(R.id.messagesRecycler);
        messageIconEditView = findViewById(R.id.messageIconEditView);
        action_image = findViewById(R.id.action_image);
    }
}