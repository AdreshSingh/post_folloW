package com.shivank.myapplication.ui.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shivank.myapplication.MainActivity;
import com.shivank.myapplication.databinding.ActivitySignupAcitivityBinding;
import com.shivank.myapplication.models.UserModel;
import com.shivank.myapplication.ui.signin.SignInActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupAcitivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    List<String> followers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupAcitivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing Up...");
        progressDialog.setMessage("please wait, creating your account");

        Intent intent = new Intent(this, MainActivity.class);

        binding.appSignUp.setOnClickListener(view->{
            String username = binding.appUserName.getText().toString();
            String userEmail=binding.appUserEmail.getText().toString();
            String password=binding.appUserPassword.getText().toString();

            if (!username.isEmpty() && !password.isEmpty() && !userEmail.isEmpty()){
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(userEmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            database.getReference().child("Users")
                                    .child(Objects.requireNonNull(mAuth.getUid()))
                                    .setValue(
                                    new UserModel(username,userEmail,password,new Date().getTime(),followers)
                            );
                            Toast.makeText(SignUpActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }else {
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.appAccountNotExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
    }
}