package com.example.messanger.models.controllers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationModelView extends AndroidViewModel {

    private final FirebaseAuth auth;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isResetSendMailSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLogOutSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }


    public MutableLiveData<Boolean> getIsLogOutSuccess() {
        return isLogOutSuccess;
    }


    public LiveData<Boolean> getIsResetSendMailSuccess() {
        return isResetSendMailSuccess;
    }

    public void logOut() {
        auth.signOut();
    }

    public LiveData<String> getError() {
        return error;
    }

    public AuthenticationModelView(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void login(String eMail, String password) {
        auth.signInWithEmailAndPassword(eMail, password)
                .addOnSuccessListener(authResult -> user.setValue(authResult.getUser()))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

    public void resetPassword(String eMail) {
        auth.sendPasswordResetEmail(eMail)
                .addOnSuccessListener(unused -> isResetSendMailSuccess.setValue(true))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
