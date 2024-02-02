package com.example.messanger.views;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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

            String email = getTrimmedText(editTextTextEmailAddress);
            String password = getTrimmedText(editTextTextPassword);
            String name = getTrimmedText(editTextName);
            String lastName = getTrimmedText(editTextTextLastName);
            String age = getTrimmedText(editTextOld);

            if (email.equals("") || password.equals("") || name.equals("") || lastName.equals("") || age.equals("")) {
                Toast.makeText(RegistrationActivity.this, R.string.fillFields, Toast.LENGTH_SHORT).show();
                return;
            }

            model.login(email, password);

            model.getError().observe(this,
                    s -> Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show());

            model.getUser().observe(RegistrationActivity.this, user -> {
                if (user!= null) {
                    Intent intent = UsersActivity.createIntent(RegistrationActivity.this);
                    startActivity(intent);
                }
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

    private String getTrimmedText(TextView textView) {
        return textView.getText().toString();
    }
}