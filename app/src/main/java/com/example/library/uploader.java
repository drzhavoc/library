package com.example.library;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class uploader extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private EditText bookNameEditText, authorNameEditText, editionEditText, amountEditText;
    private Button uploadButton;

    private Uri imageUri;

    private FirebaseFirestore db;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);

        imageView = findViewById(R.id.imageView);
        bookNameEditText = findViewById(R.id.bookNameEditText);
        authorNameEditText = findViewById(R.id.authorNameEditText);
        editionEditText = findViewById(R.id.editionEditText);
        amountEditText = findViewById(R.id.amountEditText);
        uploadButton = findViewById(R.id.uploadButton);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Storage
        storageRef = FirebaseStorage.getInstance().getReference().child("images/book_images");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadData() {
        if (imageUri != null) {
            final String bookName = bookNameEditText.getText().toString();
            final String authorName = authorNameEditText.getText().toString();
            final String edition = editionEditText.getText().toString();
            final int amount = Integer.parseInt(amountEditText.getText().toString());

            final String imageName = UUID.randomUUID().toString();
            final StorageReference imageStorageRef = storageRef.child(imageName);

            imageStorageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    // Create a new book document in Firestore
                                    Map<String, Object> bookData = new HashMap<>();
                                    bookData.put("bookName", bookName);
                                    bookData.put("authorName", authorName);
                                    bookData.put("edition", edition);
                                    bookData.put("amount", amount);
                                    bookData.put("imageUrl", imageUrl);

                                    db.collection("Books")
                                            .add(bookData)
                                            .addOnSuccessListener(new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(Object o) {
                                                    Toast.makeText(uploader.this, "Book data uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    clearFields();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(uploader.this, "Error uploading book data to Firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(uploader.this, "Error uploading image to Firebase Storage", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        imageView.setImageResource(R.drawable.ic_launcher_foreground);
        bookNameEditText.setText("");
        authorNameEditText.setText("");
        editionEditText.setText("");
        amountEditText.setText("");
        imageUri = null;
    }
}
