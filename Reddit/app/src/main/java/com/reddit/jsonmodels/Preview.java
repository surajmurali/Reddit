package com.reddit.jsonmodels;

import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by surajmuralidharagupta on 11/20/16.
 */
public class Preview {
    private ArrayList<Images> images;

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }
}
