package com.osman.alexander.asandic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;

public class Rooms extends AppCompatActivity {
    private FirebaseFirestore db;
    private CollectionReference roomRef;
    private RoomAdapter adapter;
    private TextView houseN,loc;
    private ImageView backi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        db = FirebaseFirestore.getInstance();
        roomRef = db.collection("Rooms");

        houseN = findViewById(R.id.houseName);
        loc = findViewById(R.id.location);
        backi = findViewById(R.id.backimage);

        String location = getIntent().getStringExtra("location");
        String hostel = getIntent().getStringExtra("name");
        String upp = getIntent().getStringExtra("image");
        Picasso.with(getApplicationContext()).load(upp).
                transform(new BlurTransformation(getApplicationContext(), 20, 1))
                .into(backi);
        houseN.setText(hostel);
        loc.setText(location);

        setRecyclerView();
    }

    public void backToHostels(View view) {
        finish();
    }

    private void setRecyclerView(){
        String hostel = getIntent().getStringExtra("name");
        Query query = roomRef
                .whereEqualTo("hostel",hostel)
               .orderBy("time",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<RoomList> options = new FirestoreRecyclerOptions.Builder<RoomList>()
                .setQuery(query,RoomList.class)
                .build();

        adapter = new RoomAdapter(getApplicationContext(),options,this);

        RecyclerView recyclerView = findViewById(R.id.roomRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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

    public void openBook() {
        startActivity(new Intent(this, Book.class));
    }
}
