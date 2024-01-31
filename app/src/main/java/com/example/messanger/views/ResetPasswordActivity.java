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

public class ResetPasswordActivity extends AppCompatActivity {
    AuthenticationModelView modelView;

    private TextView resetEmailActivity;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initViews();

        button.setOnClickListener(v -> {
                    String userEmail = resetEmailActivity.getText().toString();
                    if (!userEmail.equals("")) {
                        modelView.getAuth()
                                .sendPasswordResetEmail(userEmail)
                                .addOnSuccessListener(unused ->
                                        Toast.makeText(ResetPasswordActivity.this
                                                , "Password was successfully changed"
                                                , Toast.LENGTH_LONG).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(ResetPasswordActivity.this,
                                                e.getMessage(), Toast.LENGTH_LONG).show())
                        ;
                    }
                }
        );

    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        return intent;
    }

    private void initViews() {
        modelView = new ViewModelProvider(this).get(AuthenticationModelView.class);
        resetEmailActivity = findViewById(R.id.resetEmailActivity);
        button = findViewById(R.id.resetButton);
    }
}