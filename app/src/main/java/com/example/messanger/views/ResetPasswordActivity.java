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
import com.example.messanger.viewModels.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {
    ResetPasswordViewModel modelView;
    private final static String TAG_EMAIL = "email";
    private TextView resetEmailActivity;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initViews();
        observeViewModel();
        setupClickListeners();

        String mail = getIntent().getStringExtra(TAG_EMAIL);

        resetEmailActivity.setText(mail);


    }
    private void setupClickListeners() {
        button.setOnClickListener(v -> {
                    String userEmail = resetEmailActivity.getText().toString();

                    if (userEmail.equals("")) {
                        Toast.makeText(ResetPasswordActivity.this,
                                R.string.fillFields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    modelView.resetPassword(userEmail);
                }
        );
    }
    private void observeViewModel() {

        modelView.getIsResetSendMailSuccess()
                .observe(ResetPasswordActivity.this, aBoolean ->
                        Toast.makeText(ResetPasswordActivity.this
                                , "Email lent to "
                                , Toast.LENGTH_LONG).show());
        modelView.getError().observe(ResetPasswordActivity.this,
                s -> Toast.makeText(ResetPasswordActivity.this
                        , s
                        , Toast.LENGTH_LONG).show());
    }

    private void initViews() {
        modelView = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        resetEmailActivity = findViewById(R.id.resetEmailActivity);
        button = findViewById(R.id.resetButton);
    }

    public static Intent createIntent(Context context, String eMail) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(TAG_EMAIL, eMail);
        return intent;
    }
}