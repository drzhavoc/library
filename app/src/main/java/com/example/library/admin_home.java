package com.example.library;





import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class admin_home extends AppCompatActivity implements View.OnClickListener{
    private Button logout;
    private Button details;
    private Button upld;
    private Button shw;
    private FirebaseAuth firebaseAuth;
    private boolean canGoBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        firebaseAuth = FirebaseAuth.getInstance();
        logout= (Button) findViewById(R.id.logoutid);
        details=(Button) findViewById(R.id.detailsid);
        upld=(Button) findViewById(R.id.uploadid);
        shw=(Button) findViewById(R.id.showid);


        // Button selectImagesButton = findViewById(R.id.imageBT);
        logout.setOnClickListener(this);
        details.setOnClickListener(this);
        upld.setOnClickListener(this);
        shw.setOnClickListener(this);


       /* FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String firstName = documentSnapshot.getString("firstName");
                        String lastName = documentSnapshot.getString("lastName");
                        String regNo = documentSnapshot.getString("regNo");
                        String phoneNo = documentSnapshot.getString("phoneNo");
                        String email = documentSnapshot.getString("email");

                        // Populate UI elements with retrieved data
                        TextView firstNameTextView = findViewById(R.id.firstNameTextView);
                        TextView lastNameTextView = findViewById(R.id.lastNameTextView);
                        TextView regNoTextView = findViewById(R.id.regNoTextView);
                        TextView phoneNoTextView = findViewById(R.id.phoneNoTextView);
                        TextView emailTextView = findViewById(R.id.emailTextView);

                        firstNameTextView.setText(firstName);
                        lastNameTextView.setText(lastName);
                        regNoTextView.setText("reg:"+regNo);
                        phoneNoTextView.setText(phoneNo);
                        emailTextView.setText(email);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure to retrieve data from Firestore
                });*/








    }

    public void onBackPressed() {
        if (!canGoBack) {
            // Prevent going back without logging out
            Toast.makeText(this, "please sign out.", Toast.LENGTH_SHORT).show();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.logoutid)
            logOut();
        else if (view.getId()==R.id.uploadid)
        {
            Intent intent = new Intent(admin_home.this, uploader.class);
            startActivity(intent);
        }
        else if (view.getId()==R.id.showid)
        {
            Intent intent = new Intent(admin_home.this, book_details.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(admin_home.this, details.class);
            startActivity(intent);

        }


    }



    private void logOut() {
        firebaseAuth.signOut();
        finish();

        Toast.makeText(this, "Sign Out!.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(admin_home.this, MainActivity.class);
        startActivity(intent);

    }
}