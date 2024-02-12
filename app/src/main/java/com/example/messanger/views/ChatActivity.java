package com.example.messanger.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messanger.R;
import com.example.messanger.models.Message;
import com.example.messanger.models.adapters.MessagesAdapter;
import com.example.messanger.viewModels.ChatViewModel;
import com.example.messanger.viewModels.ChatViewModelFactory;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "current_user_id";
    private static final String EXTRA_OTHER_USER_ID = "other_user_id";
    private TextView titleOwnerChat;
    private View statusView;
    private RecyclerView messagesRecycler;
    private EditText messageIconEditView;
    private ImageView action_image;
    private MessagesAdapter messagesAdapter;
    private String currentUserId;
    private String otherUserId;

    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;

    public ChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);

        chatViewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);

        messagesAdapter = new MessagesAdapter(currentUserId);

        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);

        messagesRecycler.setAdapter(messagesAdapter);


        action_image.setOnClickListener(v -> {
            Message message = new Message(
                    messageIconEditView
                            .getText()
                            .toString()
                            .trim()
                    , currentUserId
                    , otherUserId);
            chatViewModel.sendMessage(message);

        });

        observeViewModel();

    }

    void observeViewModel() {
        chatViewModel.getMessages()
                .observe(this
                        , messages -> messagesAdapter
                                .setMessages(messages));
        chatViewModel.getIsError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        chatViewModel.getIsMessageSent().observe(this, wasSend -> {
            if (wasSend)
                messageIconEditView.setText("");
        });

        chatViewModel.getOtherUser().observe(this, user -> {
            String nameOwner = String.format("%s %s", user.getName(), user.getLastName());
            titleOwnerChat.setText(nameOwner);
        });
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