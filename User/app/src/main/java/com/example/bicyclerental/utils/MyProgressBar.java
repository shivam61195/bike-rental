package com.example.bicyclerental.utils;


import android.app.Dialog;
import android.content.Context;

import com.example.bicyclerental.R;

public class MyProgressBar {

    Dialog progressBar;


    public MyProgressBar(Context context) {
        progressBar = new Dialog(context);
    }

    public void showProgressBar() {
        progressBar.setContentView(R.layout.my_progress_bar);
        progressBar.show();
    }

    public void dismissProgressBar() {
        progressBar.dismiss();
    }

}
