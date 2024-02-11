package com.example.messanger.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messanger.models.Message;
import com.example.messanger.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<User> otherUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMessageSent = new MutableLiveData<>(false);

    private MutableLiveData<String> isError = new MutableLiveData<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceUsers = database.getReference("Users");
    private DatabaseReference referenceMessages = database.getReference("Messages");
    private String currentUserId;
    private String otherUserId;

    public ChatViewModel(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        initListeners();
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<User> getOtherUser() {
        return otherUser;
    }

    public LiveData<Boolean> getIsMessageSent() {
        return isMessageSent;
    }

    public LiveData<String> getIsError() {
        return isError;
    }

    public void sendMessage(Message message) {
        referenceUsers
                .child(message.getSenderId())
                .child(message.getReceiverId())
                .push()
                .setValue(message.getTextMessage())
                .addOnSuccessListener(unused -> referenceUsers
                        .child(message.getReceiverId())
                        .child(message.getSenderId())
                        .push()
                        .setValue(message.getTextMessage())
                        .addOnSuccessListener(unused1 -> isMessageSent.setValue(true))
                        .addOnFailureListener(e -> isMessageSent.setValue(false)))
                .addOnFailureListener(e -> isMessageSent.setValue(false));

    }

    private void initListeners() {
        referenceUsers
                .child(otherUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        otherUser.setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        referenceUsers
                .child(currentUserId)
                .child(otherUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Message> messageList = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Message messageResult = data.getValue(Message.class);
                            messageList.add(messageResult);
                        }
                        messages.setValue(messageList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
