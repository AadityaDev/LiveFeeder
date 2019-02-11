package com.aditya.livefeeder.utils;

import android.view.View;

import androidx.annotation.NonNull;

public class Utility {

    public static void hideProgressBar(@NonNull View progressBar) {
            progressBar.setVisibility(View.INVISIBLE);
    }

    public static void showProgressBar(@NonNull View progressBar) {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

}
