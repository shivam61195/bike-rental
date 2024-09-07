package com.example.bicyclerentaladmin;

import com.example.bicyclerentaladmin.models.BicycleData;
import com.example.bicyclerentaladmin.models.User;

import java.util.List;

public class Constants {

    private static List<BicycleData> bicycleDataList;

    public static List<BicycleData> getBicycleDataList() {
        return bicycleDataList;
    }

    public static void setBicycleDataList(List<BicycleData> bicycleDataList) {
        Constants.bicycleDataList = bicycleDataList;
    }


    private static List<User> userList;

    public static List<User> getUserList() {
        return userList;
    }

    public static void setUserList(List<User> userList) {
        Constants.userList = userList;
    }
}
