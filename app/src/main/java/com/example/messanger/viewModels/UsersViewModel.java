package com.example.messanger.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersViewModel extends AndroidViewModel {
    private final FirebaseAuth auth;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public UsersViewModel(@NonNull Application application) {
        super(application);

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> user.setValue(firebaseAuth.getCurrentUser()));
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logOut() {
        auth.signOut();
    }
}
