package com.osman.alexander.asandic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class Profile extends AppCompatActivity {
    private final static int PICK_IMAGE_REQUEST = 1;
    private Uri image_url;
    private CircleImageView profileImage;
    private EditText username, refNum, campus;
    private TextView gen, mail;
    ProgressDialog progressDialog;
    private StorageReference mStorageRef;
    FirebaseFirestore db;
    private static final String TAG = "Profile";
    private DocumentReference useRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.discoveryImage);
        username = findViewById(R.id.userEditext);
        refNum = findViewById(R.id.refNumber);
        campus = findViewById(R.id.campus);
        gen = findViewById(R.id.gender);
        mail = findViewById(R.id.mail);

        username.setEnabled(false);
        refNum.setEnabled(false);
        campus.setEnabled(false);

        db = FirebaseFirestore.getInstance();
        useRef = db.collection("Users").document();

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

          mStorageRef = FirebaseStorage.getInstance().getReference("Profiles");

        setUserImage();
    }

    public void backToDashboard(View view) {
        finish();
    }

    public void openImageChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_url = data.getData();
            Picasso.with(this).load(image_url).into(profileImage);
        }
    }

    private String getImageExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void editUserName(View view) {
        username.setEnabled(true);
    }

    public void saveUserDets(View view) {
        if (username.getText().toString().isEmpty() || refNum.getText().toString().isEmpty()
                || campus.getText().toString().isEmpty() || profileImage.getDrawable() == null) {
            Toast.makeText(this, "Field Empty", Toast.LENGTH_SHORT).show();
        } else {
            username.setEnabled(false);

        }
    }

    private void setUserImage() {
        String userid = mAuth.getCurrentUser().getUid();
        useRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String img = documentSnapshot.getString("image_url");
                        Picasso.with(Profile.this).load(img).into(profileImage);}
                        else {
                            Toast.makeText(Profile.this, "Fuck off", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}