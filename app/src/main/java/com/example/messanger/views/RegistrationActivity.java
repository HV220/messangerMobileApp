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
import com.example.messanger.models.modelviews.AuthenticationModelView;
import com.google.firebase.auth.FirebaseUser;
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

//            model.getAuth().createUserWithEmailAndPassword(email, password)
//                    .addOnSuccessListener(authResult -> {
//                        FirebaseUser firebaseUser = authResult.getUser();
//                        if (firebaseUser != null) {
//                            // Создаем объект пользователя
//                            User user = new User(name, lastName, age);
//                            // Записываем данные в базу данных Firebase
//                            databaseReference.child("users").child(firebaseUser.getUid()).setValue(user)
//                                    .addOnSuccessListener(aVoid -> {
//                                        // Пользователь успешно добавлен в базу данных
//                                        Toast.makeText(RegistrationActivity.this,
//                                                R.string.was_successful_added, Toast.LENGTH_LONG).show();
//                                    })
//                                    .addOnFailureListener(e -> {
//                                        // Обработка ошибки
//                                        Toast.makeText(RegistrationActivity.this,
//                                                e.getMessage(), Toast.LENGTH_LONG).show();
//                                    });
//                        }
//
//                        Toast.makeText(RegistrationActivity.this,
//                                authResult.getAdditionalUserInfo()
//                                        .getUsername() + R.string.was_successful_added
//                                , Toast.LENGTH_LONG).show()
//                    });
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