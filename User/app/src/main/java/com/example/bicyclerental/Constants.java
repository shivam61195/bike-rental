package com.example.bicyclerental;

import com.example.bicyclerental.models.Bicycle;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class Constants {

    private static List<Bicycle> bicycleList;

    private static String userUID;

    private static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Constants.userName = userName;
    }

    public static List<Bicycle> getBicycleList() {
        return bicycleList;
    }

    public static void setBicycleList(List<Bicycle> bicycleList) {
        Constants.bicycleList = bicycleList;
    }


    public static String getUserUID() {
        return userUID;
    }

    public static void setUserUID(String userUID) {
        Constants.userUID = userUID;
    }
}
