package com.bmacode17.instaslam.models;

import android.net.Uri;

/**
 * Created by User on 15-Apr-18.
 */

public class InstaGalary {

    private String imageName;
    private String imagePath;
    private static String DRAWABLE = "drawable/";

    public InstaGalary(String imageName, String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImagePath() {
        return DRAWABLE + imagePath;
    }
}
