package com.osman.alexander.asandic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class Dashboard extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Toolbar toolbar;
    FlipperLayout flipper;
    TextView emailView, phoneView;
    CircleImageView userImage;
    private StorageReference mStorageRef;
    private CollectionReference userRef;
    private DocumentReference useRef;


    private static final String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        flipper = findViewById(R.id.flipper);

        emailView = findViewById(R.id.emailView);
        phoneView = findViewById(R.id.phoneView);
        userImage = findViewById(R.id.userImageDash);

        mStorageRef = FirebaseStorage.getInstance().getReference("Profiles");
        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        setLayout();
        getUserDetails();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutItem) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLayout() {
        int[] images = {
                R.drawable.entrance1, R.drawable.pinamang, R.drawable.room1,
                R.drawable.hostel1, R.drawable.ho, R.drawable.hoste1};

        for (int i = 0; i < 6; i++) {
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageDrawable(images[i]);
            flipper.addFlipperView(view);

        }
    }

    private void getUserDetails() {
        try {
            String e = "Email: ";
            emailView.setText(e + mAuth.getCurrentUser().getEmail());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void openUserProfile(View view) {
        startActivity(new Intent(this, Profile.class));
    }

    public void openUserPayment(View view) {
        startActivity(new Intent(this, Payments.class));
    }

    public void openNewsFeed(View view) {
        startActivity(new Intent(this, NewsFeed.class));
    }

    public void openNotions(View view) {
        startActivity(new Intent(this, Notifications.class));
    }

    public void openAvailableHostels(View view) {
        startActivity(new Intent(this, Hostels.class));
    }

    public void openContact(View view) {
        startActivity(new Intent(this, Contact.class));
    }


}

