package com.example.messanger.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
public class ResetPasswordViewModel extends AndroidViewModel {
    private final FirebaseAuth auth;
    private final MutableLiveData<Boolean> isResetSendMailSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsResetSendMailSuccess() {
        return isResetSendMailSuccess;
    }

    public void resetPassword(String eMail) {
        auth.sendPasswordResetEmail(eMail)
                .addOnSuccessListener(unused -> isResetSendMailSuccess.setValue(true))
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }
}
