package com.example.messanger.views;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.messanger.R;

import com.example.messanger.viewModels.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity {
    private RegistrationViewModel model;
    private TextView editTextTextEmailAddress;
    private TextView editTextTextPassword;
    private TextView editTextName;
    private TextView editTextTextLastName;
    private TextView editTextOld;
    private Button buttonRegistration;
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        observeViewModel();
        setupClickListeners();
    }


    private void setupClickListeners() {
        buttonRegistration.setOnClickListener(v -> {
            String email = getTrimmedText(editTextTextEmailAddress);
            String password = getTrimmedText(editTextTextPassword);
            String name = getTrimmedText(editTextName);
            String lastName = getTrimmedText(editTextTextLastName);
            String ageText = getTrimmedText(editTextOld);

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastName.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(RegistrationActivity.this, R.string.fillFields, Toast.LENGTH_SHORT).show();
                return;
            }
            int addedAge;
            try {
                addedAge = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                Log.d(TAG, "NumberFormatException: " + e.getMessage());
                Toast.makeText(RegistrationActivity.this, "Please enter a valid age.", Toast.LENGTH_SHORT).show();
                return;
            }

            model.createUser(email, password, name, lastName, addedAge);
        });
    }


    private void observeViewModel() {
        model.getError().observe(this,
                s -> Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show());

        model.getUser().observe(RegistrationActivity.this, user -> {
            if (user != null) {
                Intent intent = UsersActivity.createIntent(RegistrationActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        model = new ViewModelProvider(RegistrationActivity.this).get(RegistrationViewModel.class);

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