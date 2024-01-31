package com.example.messanger.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.messanger.R;
import com.example.messanger.models.controllers.AuthenticationModelView;

public class UsersActivity extends AppCompatActivity {
    AuthenticationModelView model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initViews();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut){
            model.getAuth().signOut();
            Intent intent = MainActivity.createIntent(UsersActivity.this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        model = new ViewModelProvider(UsersActivity.this).get(AuthenticationModelView.class);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, UsersActivity.class);
        return intent;
    }
}