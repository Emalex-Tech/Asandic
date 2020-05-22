package com.osman.alexander.asandic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class Hostels extends AppCompatActivity {
    private FirebaseFirestore db;
    private CollectionReference hostelRef;
    private HostelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels);

        db = FirebaseFirestore.getInstance();
        hostelRef = db.collection("Hostels");

        setRecyclerView();
    }

    public void backToDashboard(View view) {
        finish();
    }

    private void setRecyclerView(){
        Query query = hostelRef.orderBy("time",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<HostelList> options = new FirestoreRecyclerOptions.Builder<HostelList>()
                .setQuery(query,HostelList.class)
                .build();

        adapter = new HostelAdapter(getApplicationContext(),options);

        RecyclerView recyclerView = findViewById(R.id.hostelRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new HostelAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String mHostelName = documentSnapshot.getString("name");
                String mHostelLocation = documentSnapshot.getString("location");
                String mHostelImage = documentSnapshot.getString("image_url");

                Intent intent = new Intent(getApplicationContext(),Rooms.class);
                intent.putExtra("name",mHostelName);
                intent.putExtra("location",mHostelLocation);
                intent.putExtra("image",mHostelImage);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
