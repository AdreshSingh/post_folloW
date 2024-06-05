package com.shivank.myapplication.ui.signin;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shivank.myapplication.MainActivity;
import com.shivank.myapplication.R;
import com.shivank.myapplication.databinding.ActivitySignInBinding;
import com.shivank.myapplication.models.UserModel;
import com.shivank.myapplication.ui.signup.SignUpActivity;

import java.util.Date;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    public static final String DB="Users";
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("please wait, logging into account..");

        binding.appSignIn.setOnClickListener(view ->{
            progressDialog.show();
            String username = binding.appUserName.getText().toString();
            String password=binding.appUserPassword.getText().toString();
            HashMap<String,Object> loginTimeStamp = new HashMap<>();
            loginTimeStamp.put("timeStamp",new Date().getTime());
            if (!username.isEmpty() && !password.isEmpty()){
                mAuth.signInWithEmailAndPassword(username,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    firebaseDatabase.getReference().child("Users").child(mAuth.getUid())
                                            .updateChildren(loginTimeStamp);
                                    Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.appAccountExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        if (mAuth.getUid() != null){
            startActivity(intent);
            this.finish();
        }
    }
}