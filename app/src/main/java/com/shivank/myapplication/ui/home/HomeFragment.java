package com.shivank.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivank.myapplication.adapters.UserModelAdapter;
import com.shivank.myapplication.databinding.FragmentHomeBinding;
import com.shivank.myapplication.models.UserModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    ArrayList<UserModel> userModelArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        userModelArrayList = new ArrayList<>();
        UserModelAdapter adapter = new UserModelAdapter(userModelArrayList,getContext(),firebaseAuth.getUid());

        firebaseDatabase.getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot singleSnapShot: snapshot.getChildren()){
                                    UserModel user = singleSnapShot.getValue(UserModel.class);
                                    userModelArrayList.add(user);
                                }
                                binding.appHomeProgressBar.setVisibility(View.GONE);
                                binding.appRecyclerHome.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.appRecyclerHome.setLayoutManager(linearLayoutManager);
        binding.appRecyclerHome.setAdapter(adapter);

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}