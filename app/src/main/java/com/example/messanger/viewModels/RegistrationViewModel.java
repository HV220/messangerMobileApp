package com.example.messanger.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.messanger.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends AndroidViewModel {
    private final FirebaseAuth auth;
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

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

    public void createUser(String eMail,
                           String password,
                           String name,
                           String lastName,
                           int age) {
        auth.createUserWithEmailAndPassword(eMail, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser addedUser = authResult.getUser();

                    if (addedUser == null) {
                        return;
                    }
                    User user = new User(addedUser.getUid()
                            , name, lastName, age
                            , false);
                    databaseReference.child(addedUser.getUid()).setValue(user);


                })
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
