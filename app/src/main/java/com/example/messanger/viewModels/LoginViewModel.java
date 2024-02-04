package com.example.messanger.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isResetSendMailSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<Boolean> getIsResetSendMailSuccess() {
        return isResetSendMailSuccess;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public void login(String eMail, String password) {
        auth.signInWithEmailAndPassword(eMail, password)
                .addOnSuccessListener(authResult -> user.setValue(authResult.getUser()))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

    public void createUser(String eMail, String password) {
        auth.createUserWithEmailAndPassword(eMail, password)
                .addOnSuccessListener(authResult -> user.setValue(authResult.getUser()))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

    public void resetPassword(String eMail) {
        auth.sendPasswordResetEmail(eMail)
                .addOnSuccessListener(unused -> isResetSendMailSuccess.setValue(true))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
