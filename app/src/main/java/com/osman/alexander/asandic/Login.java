package com.osman.alexander.asandic;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {

    Button readMore, joinClub, login;
    EditText email, password, firstName, lastName, emailAdd, phone, id, pass, confirmPassword,
            username, campus;
    RadioButton male, female;
    RadioGroup radioGroup;
    LinearLayout signIn, signup;
    RelativeLayout pc;
    CircleImageView showProfileImage;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog;
    private static final String TAG = "Login";
    private final static int PICK_IMAGE_REQUEST = 1;
    private Uri image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Profiles");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        //default login page
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        joinClub = findViewById(R.id.join);
        signIn = findViewById(R.id.signlayout);
        signup = findViewById(R.id.signupLayout);
        pc = findViewById(R.id.profileContent);


        //signup registration form
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailAdd = findViewById(R.id.emailAdd);
        phone = findViewById(R.id.phone);
        id = findViewById(R.id.id);
        pass = findViewById(R.id.pass);
        radioGroup = findViewById(R.id.radioGroup);
        confirmPassword = findViewById(R.id.passConfirm);
        showProfileImage = findViewById(R.id.discoveryImage);
        username = findViewById(R.id.username);
        campus = findViewById(R.id.camp);

    }


    //open dashboard code
    public void openDashboard(View v) {
        progressDialog.show();
        String emailAd = email.getText().toString();
        String passw = password.getText().toString();
        if (checkConnectivity()) {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Empty field", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            } else {
                mAuth.signInWithEmailAndPassword(emailAd, passw)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Invalid Credentials",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        });
            }
        } else if (!checkConnectivity()) {
            Toast.makeText(Login.this, "No connection", Toast.LENGTH_SHORT).show();
        }

    }

    // check internet connectivity
    public boolean checkConnectivity() {
        boolean haveMobileData = false;
        boolean haveWifi = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("wifi") && info.isConnected())
                haveMobileData = true;
            if (info.getTypeName().equalsIgnoreCase("mobile") && info.isConnected())
                haveWifi = true;
        }
        return haveMobileData || haveWifi;
    }

    //radio button toggle code
    public void radioChecked(View v) {
        int radio = radioGroup.getCheckedRadioButtonId();
        male.findViewById(radio);
        female.findViewById(radio);
    }

    // open Signup to register
    public void openSignUp(View v) {
        if (!joinClub.getText().toString().equals("LOGIN")) {
            if (checkConnectivity()) {
                signIn.setVisibility(View.GONE);
                signup.setVisibility(View.VISIBLE);
                joinClub.setText("LOGIN");
                emptyText();
            } else if (!checkConnectivity()) {
                Toast.makeText(Login.this, "No connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            signIn.setVisibility(View.VISIBLE);
            signup.setVisibility(View.GONE);
            joinClub.setText("REGISTER");
        }

    }

    public void registerUser(View v) {
        progressDialog.show();
        String email = emailAdd.getText().toString();
        String password = pass.getText().toString();
        if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() ||
                emailAdd.getText().toString().isEmpty() || radioGroup.getCheckedRadioButtonId() == -1 ||
                phone.getText().toString().isEmpty() || id.getText().toString().isEmpty() || pass.getText().toString().isEmpty() ||
                confirmPassword.getText().toString().isEmpty() || username.getText().toString().isEmpty() || campus.getText()
                .toString().isEmpty() || showProfileImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.user).getConstantState()
        ) {
            Toast.makeText(Login.this, "Empty field", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            if (!pass.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                Toast.makeText(Login.this, "Password don't match", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            saveUserDetailsToFireStore();
                            progressDialog.dismiss();
                            startActivity(new Intent(this,Dashboard.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    });
        }
    }

    private void saveUserDetailsToFireStore() {
        final StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." +
                getImageExtension(image_url));

        storageReference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                int genid = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(genid);
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String userId = FirebaseAuth.getInstance().getUid();
                String gender = radioButton.getText().toString();
                String tel = phone.getText().toString();
                String refId = id.getText().toString();
                String us = username.getText().toString();
                String c = campus.getText().toString();

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String u = uri.toString();
                        // Add a new document with a generated ID
                        db.collection("Users")
                                .add(new UserData(fName, lName, userId, gender, tel, refId, us, u, c))
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Intent intent = new Intent();
                                        intent.putExtra("img",u);
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                        Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void emptyText() {
        firstName.setText("");
        lastName.setText("");
        emailAdd.setText("");
        male.setChecked(false);
        female.setChecked(false);
        phone.setText("");
        id.setText("");
        pass.setText("");
        confirmPassword.setText("");
        username.setText("");
        campus.setText("");
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            updateUI(user);
        }
    }

    public void openImageChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getImageExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_url = data.getData();
            Picasso.with(this).load(image_url).into(showProfileImage);
        }
    }

    public void openSite(View view) {
    }
}
