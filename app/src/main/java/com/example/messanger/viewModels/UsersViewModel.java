package com.example.messanger.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.messanger.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    private final FirebaseAuth auth;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private final MutableLiveData<List<User>> userList = new MutableLiveData<>();

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

    public UsersViewModel(@NonNull Application application) {
        super(application);

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> user.setValue(firebaseAuth.getCurrentUser()));
        loadUsersFromDB();
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<List<User>> getUsers() {
        return userList;
    }

    public void logOut() {
        setOnUserOnline(false);
        auth.signOut();
    }

    public void setOnUserOnline(boolean userOnline) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) return;

        databaseReference.child(user.getUid())
                .child("online")
                .setValue(userOnline);
    }

    private void loadUsersFromDB() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> tmpUserList = new ArrayList<>();
                FirebaseUser currentUser = auth.getCurrentUser();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    User user = datasnapshot.getValue(User.class);
                    if (!((currentUser == null)
                            || (user == null)
                            || currentUser.getUid().equals(user.getId())))
                        tmpUserList.add(user);
                }
                userList.setValue(tmpUserList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
