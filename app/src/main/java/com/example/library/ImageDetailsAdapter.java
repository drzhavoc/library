package com.example.library;
// ImageDetailsAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
// Import Glide library
import java.util.List;

public class ImageDetailsAdapter extends RecyclerView.Adapter<ImageDetailsAdapter.ViewHolder> {
    private Context context;
    private List<ImageDetails> imageDetailsList;

    public ImageDetailsAdapter(Context context, List<ImageDetails> imageDetailsList) {
        this.context = context;
        this.imageDetailsList = imageDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageDetails imageDetails = imageDetailsList.get(position);

        // Load and display the image from the URL using Glide
        Glide.with(context)
                .load(imageDetails.getImageUrl()) // Pass the URL here
                .into(holder.imageView);

        holder.bookNameTextView.setText(imageDetails.getBookName());
        holder.authorNameTextView.setText(imageDetails.getAuthorName());
        // Bind other details as needed
    }

    @Override
    public int getItemCount() {
        return imageDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookNameTextView;
        TextView authorNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            authorNameTextView = itemView.findViewById(R.id.authorNameTextView);
            // Initialize other views here
        }
    }
}
