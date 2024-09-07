package com.example.bicyclerentaladmin.models;

public class Order {
    String bicycleName;
    String userName;
    String imageURL;

    public Order(String bicycleName, String userName, String imageURL) {
        this.bicycleName = bicycleName;
        this.userName = userName;
        this.imageURL = imageURL;
    }

    public Order() {
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
