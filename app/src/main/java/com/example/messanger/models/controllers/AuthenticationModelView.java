package com.example.messanger.models.controllers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationModelView extends AndroidViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public AuthenticationModelView(@NonNull Application application) {
        super(application);
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
}
