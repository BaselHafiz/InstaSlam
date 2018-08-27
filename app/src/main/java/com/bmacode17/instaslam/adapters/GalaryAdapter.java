package com.bmacode17.instaslam.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmacode17.instaslam.R;
import com.bmacode17.instaslam.activities.MediaActivity;
import com.bmacode17.instaslam.holders.GalaryViewHolder;
import com.bmacode17.instaslam.models.InstaGalary;

import java.util.ArrayList;

/**
 * Created by User on 15-Apr-18.
 */

public class GalaryAdapter extends RecyclerView.Adapter<GalaryViewHolder> {

    private static final String TAG = GalaryAdapter.class.getSimpleName();
    private ArrayList<InstaGalary> myItems;
    public ImageView selectedImage;

    public GalaryAdapter(ArrayList<InstaGalary> myItems) {
        this.myItems= myItems;
    }

    @Override
    public GalaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View galaryLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_galary,parent,false);

        GalaryViewHolder viewHolder = new GalaryViewHolder(galaryLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GalaryViewHolder holder, int position) {

        final InstaGalary item = myItems.get(position);
        holder.updateUI(item);
        final GalaryViewHolder vHolder = holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vHolder.updateSelectedImage(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }
}
