package com.example.messanger.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messanger.R;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView aboutUserShortInfo;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            aboutUserShortInfo = itemView.findViewById(R.id.userItem);
        }
    }
}
