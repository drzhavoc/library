package com.example.library;

// MainActivity.java
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class book_details extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageDetailsAdapter adapter;
    private List<ImageDetails> imageDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageDetailsList = new ArrayList<>();
        adapter = new ImageDetailsAdapter(this, imageDetailsList);
        recyclerView.setAdapter(adapter);

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference booksRef = db.collection("Books");

        // Retrieve data from Firestore
        booksRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   /* for (DocumentSnapshot document : task.getResult()) {
                        // Parse data and create ImageDetails objects
                        String bookName = document.getString("bookName");
                        String authorName = document.getString("authorName");
                        //String edition = document.getString("edition");
                        //String amount = document.getString("amount");
                        // Parse other details
                        int imageResource = R.drawable.burisonamoni; // Use a placeholder image
                       // ImageDetails imageDetails = new ImageDetails(imageResource, bookName, authorName,edition,amount);
                        ImageDetails imageDetails = new ImageDetails(imageResource, bookName,authorName);
                        imageDetailsList.add(imageDetails);
                    }*/
                    // Inside your MainActivity.java where you retrieve data from Firestore
                    for (DocumentSnapshot document : task.getResult()) {
                        // Parse data, including the image URL
                        String bookName = document.getString("bookName");
                        String authorName = document.getString("authorName");
                        String imageUrl = document.getString("imageUrl"); // Assuming the field name is "imageUrl"

                        ImageDetails imageDetails = new ImageDetails(imageUrl, bookName, authorName);
                        imageDetailsList.add(imageDetails);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle the error
                }
            }
        });
    }
}
