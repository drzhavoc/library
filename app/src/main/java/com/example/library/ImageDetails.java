package com.example.library;

// ImageDetails.java
public class ImageDetails {
    private String imageUrl;
    private String bookName;
    private String authorName;
   // private String edition;
    //private String amount;

    public ImageDetails(String imageUrl, String bookName, String authorName) {
        this.imageUrl = imageUrl;
        this.bookName = bookName;
        this.authorName = authorName;
      //  this.edition = edition;
      //  this.amount= amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }
    /*public String getEdition() {
        return edition;
    }
    public String getAmount() {
        return amount;
    }*/

    // Add getters for other details
}
