package com.shivank.myapplication.ui.posts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.shivank.myapplication.R;
import com.shivank.myapplication.databinding.ActivityPostAddBinding;
import com.shivank.myapplication.models.PostModel;

import java.util.Date;

public class PostAddActivity extends AppCompatActivity {
    ActivityPostAddBinding binding;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityPostAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String userUid =getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");

        ProgressDialog progressDialog = new ProgressDialog(PostAddActivity.this);
        progressDialog.setTitle("Sending");
        progressDialog.setMessage("posting a message....");

        firebaseDatabase = FirebaseDatabase.getInstance();

       binding.postButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog.show();
               String postTitle = binding.titleEditText.getText().toString(),
                       postBody = binding.bodyEditText.getText().toString();
             if (!(postTitle.isEmpty() && postBody.isEmpty())){
                 PostModel post = new PostModel(userUid,userName,postTitle,postBody,new Date().getTime());
                 firebaseDatabase.getReference().child("Posts")
                         .child(userUid)
                         .push()
                         .setValue(post)
                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 Toast.makeText(PostAddActivity.this, "posted successfully", Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                             }
                         });
                 binding.titleEditText.getText().clear();
                 binding.bodyEditText.getText().clear();
             }else {
                 Toast.makeText(PostAddActivity.this, "title or body of post can't be empty", Toast.LENGTH_SHORT).show();
             }
           }
       });
    }
}