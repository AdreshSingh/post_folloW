package com.shivank.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivank.myapplication.R;
import com.shivank.myapplication.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserModelAdapter extends RecyclerView.Adapter<UserModelAdapter.ViewHolder> {
    public UserModelAdapter(ArrayList<UserModel> users, Context ctx,String currentUserId) {
        this.users = users;
        this.ctx = ctx;
        this.currentUserId = currentUserId;
    }

    ArrayList<UserModel> users;
    Context ctx;
    String currentUserId;
    FirebaseDatabase mDatabase;
    boolean sucess = false;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.user_listing_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user=users.get(position);
        holder.appUserRank.setText(String.valueOf((position+1)));
        holder.appUsername.setText(String.valueOf(user.getUserName()));

        holder.favBtn.setImageDrawable(ctx.getDrawable(R.drawable.heart_outlined));
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUser(user.getUserId());
                holder.favBtn.setImageDrawable((sucess?ctx.getDrawable(R.drawable.heart_filled):ctx.getDrawable(R.drawable.heart_outlined)));
                sucess=false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView appUsername,appUserFollowers,appUserRank;
        ImageButton favBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appUsername = itemView.findViewById(R.id.appUserName);
            appUserFollowers=itemView.findViewById(R.id.appUserFollower);
            appUserRank = itemView.findViewById(R.id.appUserRank);
            favBtn = itemView.findViewById(R.id.favBtn);
        }
    }

    private void followUser(String targetUserId) {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference currentUserRef = mDatabase.getReference().child("Users").child(currentUserId).child("following");

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> followingList = (List<String>) snapshot.getValue();

                if (followingList == null) {
                    followingList = new ArrayList<>();
                }

                if (!followingList.contains(targetUserId)) {
                    followingList.add(targetUserId);
                } else {
                    followingList.remove(targetUserId);
                }

                currentUserRef.setValue(followingList).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sucess = true;
                        Toast.makeText(ctx, "User follow/unfollow updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ctx, "Failed to update follow unfollow."+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
