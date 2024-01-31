package com.example.messanger.views;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;


import com.example.messanger.R;
import com.example.messanger.models.controllers.AuthenticationModelView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private AuthenticationModelView model;
    private TextView editTextTextEmailAddress;
    private TextView editTextTextPassword;
    private TextView editTextName;
    private TextView editTextTextLastName;
    private TextView editTextOld;


    private DatabaseReference databaseReference;
    private Button buttonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();

        buttonRegistration.setOnClickListener(v -> {

            String email = editTextTextEmailAddress.getText().toString().trim();
            String password = editTextTextPassword.getText().toString().trim();
            String name = editTextName.getText().toString().trim();
            String lastName = editTextTextLastName.getText().toString().trim();
            String age = editTextOld.getText().toString().trim();

            model.getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {

                        Intent intent = UsersActivity.createIntent(RegistrationActivity.this);
                        startActivity(intent);
                    });
        });

    }

    private void initViews() {
        model = new ViewModelProvider(RegistrationActivity.this).get(AuthenticationModelView.class);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextTextLastName = findViewById(R.id.editTextTextLastName);
        editTextOld = findViewById(R.id.editTextOld);
        buttonRegistration = findViewById(R.id.buttonRegistration);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);

        return intent;
    }
}