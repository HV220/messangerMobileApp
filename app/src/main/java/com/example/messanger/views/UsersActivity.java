package com.example.messanger.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.messanger.R;
import com.example.messanger.models.adapters.UsersAdapter;
import com.example.messanger.viewModels.UsersViewModel;

public class UsersActivity extends AppCompatActivity {
    UsersViewModel model;

    UsersAdapter users;
    RecyclerView usersRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        model = new ViewModelProvider(UsersActivity.this).get(UsersViewModel.class);

        initViews();
        observeViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut) {
            model.logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeViewModel() {
        model.getUser().observe(UsersActivity.this, firebaseUser -> {
            if (firebaseUser == null) {
                Intent intent = LoginActivity.createIntent(UsersActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        users = new UsersAdapter();
        usersRecycle = findViewById(R.id.usersRecycler);
        usersRecycle.setAdapter(users);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, UsersActivity.class);
        return intent;
    }
}