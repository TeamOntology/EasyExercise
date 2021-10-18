package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.PublicPlan;
import com.example.myapplication.ui.activities.ChatRoomActivity;
import com.example.myapplication.ui.adapters.CommunityAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    View view;
    private List<PublicPlan> publicPlanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_community, container, false);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ontology-5ae5d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference mDatabase = database.getReference().child("community");

        recyclerView = (RecyclerView) view.findViewById(R.id.community_plan_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommunityAdapter(publicPlanList, publicPlan -> {
            //on click event

            Intent intent = new Intent(getContext().getApplicationContext(), ChatRoomActivity.class);
            intent.putExtra("plan", publicPlan.getPlan());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot s: task.getResult().getChildren()){
                        PublicPlan receivePlan = s.getValue(PublicPlan.class);
                        if(receivePlan == null){
                            Log.e("firebase", "Data = null", task.getException());
                        }
                        else{
                            publicPlanList.add(receivePlan);
                            adapter.notifyItemInserted(publicPlanList.size() - 1);
                        }
                    }
                }
            }
        });




        return view;
    }
}
