package com.example.bicyclerental.models;

public class Booking {

    private String bicycleName;

    private String userName;

    private int hour;

    private String imageURL;

    private float Rate;



    public Booking(String bicycleName, String userName, int hour, String imageURL, float rate) {
        this.bicycleName = bicycleName;
        this.userName = userName;
        this.hour = hour;
        this.imageURL = imageURL;
        Rate = rate;
    }

    public Booking() {
    }

    public String getBicycleName() {
        return bicycleName;
    }

    public void setBicycleName(String bicycleName) {
        this.bicycleName = bicycleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(float rate) {
        Rate = rate;
    }
}
