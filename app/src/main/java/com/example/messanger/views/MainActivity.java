package com.example.messanger.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messanger.R;
import com.example.messanger.models.modelviews.AuthenticationModelView;
import com.google.android.gms.tasks.OnFailureListener;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private TextView editTextTextEmailAddress;
    private TextView editTextTextPassword;

    private TextView resetPassword;
    private TextView registration;
    private Button button;

    AuthenticationModelView modelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        button.setOnClickListener(v -> {
            String userEmail = editTextTextEmailAddress.getText().toString();
            String userPassword = editTextTextPassword.getText().toString();

            modelView.getAuth().signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnSuccessListener(authResult -> {
                        Intent intent = UsersActivity.createIntent(this);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        registration.setOnClickListener(v -> {
            Intent intent = RegistrationActivity.createIntent(MainActivity.this);
            startActivity(intent);
        });

        resetPassword.setOnClickListener(v -> {
            String userEmail = editTextTextEmailAddress.getText().toString();

            modelView.getAuth().sendPasswordResetEmail(userEmail).addOnSuccessListener(unused ->
                            Toast.makeText(MainActivity.this, "Password was successfully changed",
                                    Toast.LENGTH_LONG).show())
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    private void initViews() {
        modelView = new ViewModelProvider(this).get(AuthenticationModelView.class);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        resetPassword = findViewById(R.id.resetPassword);
        registration = findViewById(R.id.registration);
        button = findViewById(R.id.button);
    }
}