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
import com.example.messanger.models.User;
import com.example.messanger.models.adapters.UsersAdapter;
import com.example.messanger.viewModels.UsersViewModel;

public class UsersActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "current_user_id";
    private String currentUserId;
    UsersViewModel model;

    UsersAdapter usersAdapter;
    RecyclerView usersRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        model = new ViewModelProvider(UsersActivity.this).get(UsersViewModel.class);
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
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
        usersAdapter.setUserClickListener(user -> {
            Intent intent = ChatActivity.newIntent(UsersActivity.this, currentUserId, user.getId());
            startActivity(intent);
        });

        model.getUsers().observe(UsersActivity.this, users -> usersAdapter.setUsers(users));
    }

    private void initViews() {
        usersAdapter = new UsersAdapter();
        usersRecycle = findViewById(R.id.usersRecycler);
        usersRecycle.setAdapter(usersAdapter);
    }

    public static Intent createIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        return intent;
    }
}