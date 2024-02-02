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

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";
    private TextView editTextTextEmailAddress;
    private TextView editTextTextPassword;
    private TextView resetPassword;
    private TextView registration;
    private Button button;

    AuthenticationModelView modelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        modelView = new ViewModelProvider(this).get(AuthenticationModelView.class);
        initViews();
        observeViewModel();
        setupClickListeners();


    }

    private void initViews() {
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        resetPassword = findViewById(R.id.resetPassword);
        registration = findViewById(R.id.registration);
        button = findViewById(R.id.button);
    }

    private void observeViewModel() {
        modelView.getError().observe(this,
                s -> {
                    if (s != null) {
                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

        modelView.getUser().observe(LoginActivity.this, user -> {
            if (user != null) {
                Intent intent = UsersActivity.createIntent(LoginActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupClickListeners() {
        button.setOnClickListener(v -> {
            String userEmail = editTextTextEmailAddress.getText().toString();
            String userPassword = editTextTextPassword.getText().toString();

            if (userEmail.equals("") || userPassword.equals("")) {
                Toast.makeText(LoginActivity.this
                        , R.string.fillFields, Toast.LENGTH_SHORT).show();
                return;
            }

            modelView.login(userEmail, userPassword);
        });

        registration.setOnClickListener(v -> {
            Intent intent = RegistrationActivity.createIntent(LoginActivity.this);
            startActivity(intent);
        });

        resetPassword.setOnClickListener(v -> {

            Intent intent = ResetPasswordActivity.createIntent(LoginActivity.this
                    , editTextTextEmailAddress.getText().toString());
            startActivity(intent);

        });
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }
}