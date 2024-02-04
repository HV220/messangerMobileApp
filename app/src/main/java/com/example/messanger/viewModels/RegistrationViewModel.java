package com.example.messanger.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationViewModel extends AndroidViewModel {
    private final FirebaseAuth auth;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }
    public LiveData<FirebaseUser> getUser() {
        return user;
    }
    public LiveData<String> getError() {
        return error;
    }

    public void createUser(String eMail, String password) {
        auth.createUserWithEmailAndPassword(eMail, password)
                .addOnSuccessListener(authResult -> user.setValue(authResult.getUser()))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
