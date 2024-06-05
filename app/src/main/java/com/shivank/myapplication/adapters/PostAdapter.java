package com.shivank.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shivank.myapplication.R;
import com.shivank.myapplication.models.PostModel;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public PostAdapter(ArrayList<PostModel> posts, Context ctx) {
        this.posts = posts;
        this.ctx = ctx;
    }

    ArrayList<PostModel> posts;
    Context ctx;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.user_posts,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel post = posts.get(position);

        holder.userName.setText(post.getPostUserName());
        holder.postTitle.setText(post.getPostTitle());
        holder.postInfo.setText(post.getPostInfo());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName,postTitle,postInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.appUserName);
            postTitle = itemView.findViewById(R.id.appUserPostTitle);
            postInfo = itemView.findViewById(R.id.appPostInfo);
        }
    }

}
