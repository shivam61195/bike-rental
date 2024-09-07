package com.example.bicyclerentaladmin.models;

public class BicycleData {
    private String name;
    private float rate;
    private float rating;
    private String description;
    private String imageURL;
    private int Quantity;

    public BicycleData(String name, float rate, float rating, String description, String imageURL, int quantity) {
        this.name = name;
        this.rate = rate;
        this.rating = rating;
        this.description = description;
        this.imageURL = imageURL;
        Quantity = quantity;
    }

    public BicycleData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
