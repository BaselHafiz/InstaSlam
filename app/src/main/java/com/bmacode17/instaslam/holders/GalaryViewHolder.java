package com.bmacode17.instaslam.holders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bmacode17.instaslam.BuildConfig;
import com.bmacode17.instaslam.R;
import com.bmacode17.instaslam.activities.MediaActivity;
import com.bmacode17.instaslam.models.InstaGalary;

import java.io.File;


/**
 * Created by User on 15-Apr-18.
 */

public class GalaryViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_imageName;
        private ImageView imageView_imageResource;

        public GalaryViewHolder(View itemView) {
            super(itemView);
            imageView_imageResource = (ImageView) itemView.findViewById(R.id.imageResource);
            textView_imageName = (TextView) itemView.findViewById(R.id.imageName);
        }

        public void updateUI(InstaGalary item){

            String uri = item.getImagePath();
            int resource = imageView_imageResource.getResources()
                    .getIdentifier(uri, null, imageView_imageResource.getContext().getPackageName());
            imageView_imageResource.setImageResource(resource);
            textView_imageName.setText(item.getImageName());
        }

        private static final String TAG = "Basel";

        public void updateSelectedImage(InstaGalary item) {

            String uri = item.getImagePath();
            ImageView imageView_selectedImage = MediaActivity.getSelectedImageView();
            int resource = imageView_selectedImage.getResources()
                    .getIdentifier(uri, null, imageView_selectedImage.getContext().getPackageName());
            imageView_selectedImage.setImageResource(resource);
        }
    }
