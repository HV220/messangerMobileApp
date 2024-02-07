package com.example.messanger.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messanger.R;
import com.example.messanger.models.Message;
import com.example.messanger.models.adapters.MessagesAdapter;
import com.example.messanger.viewModels.UsersViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "current_user_id";
    private static final String EXTRA_OTHER_USER_ID = "other_user_id";
    private TextView titleOwnerChat;
    private View statusView;
    private RecyclerView messagesRecycler;
    private EditText messageIconEditView;
    private ImageView action_image;
    private MessagesAdapter messagesAdapter;
    private UsersViewModel viewModel;
    private String currentUserId;
    private String otherUserId;

    public ChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);

        Log.d("ChatActivity", currentUserId);
        Log.d("ChatActivity", otherUserId);

        List<Message> messages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("Test1" + i, currentUserId, otherUserId);
            messages.add(message);

        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message("Test2" + i, otherUserId, currentUserId);
            messages.add(message);
        }
        messagesAdapter = new MessagesAdapter(currentUserId);
        messagesAdapter.setMessages(messages);

        viewModel = new ViewModelProvider(ChatActivity.this).get(UsersViewModel.class);



        messagesRecycler.setAdapter(messagesAdapter);
    }

    private void initViews() {
        titleOwnerChat = findViewById(R.id.titleOwnerChat);
        statusView = findViewById(R.id.statusView);
        messagesRecycler = findViewById(R.id.messagesRecycler);
        messageIconEditView = findViewById(R.id.messageIconEditView);
        action_image = findViewById(R.id.action_image);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);

        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);

        return intent;
    }
}