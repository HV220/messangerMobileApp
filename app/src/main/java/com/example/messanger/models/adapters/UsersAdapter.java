package com.example.messanger.models.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messanger.R;
import com.example.messanger.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private onUserClickListener userClickListener;

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        if (user == null) return;

        String aboutUser = String.format("%s, %s, %s", user.getName(), user.getLastName(), user.getAge());
        holder.aboutUserShortInfo.setText(aboutUser);

        int bgResId;
        if (user.getOnline()) {
            bgResId = R.drawable.circle_green;
        } else {
            bgResId = R.drawable.circle_red;
        }
        Drawable image = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);

        holder.isOnlineImage.setImageDrawable(image);

        holder.itemView.setOnClickListener(v -> {
            if (userClickListener != null) {
                userClickListener.onClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUserClickListener(onUserClickListener userClickListener) {
        this.userClickListener = userClickListener;
    }

    private interface onUserClickListener {
        void onClick();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView aboutUserShortInfo;
        private final ImageView isOnlineImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            aboutUserShortInfo = itemView.findViewById(R.id.userItem);
            isOnlineImage = itemView.findViewById(R.id.isOnlineImage);
        }
    }
}
