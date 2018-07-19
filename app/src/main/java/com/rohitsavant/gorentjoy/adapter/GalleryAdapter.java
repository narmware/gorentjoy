package com.rohitsavant.gorentjoy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.rohitsavant.gorentjoy.R;
import com.rohitsavant.gorentjoy.fragment.ListingFragment;
import com.rohitsavant.gorentjoy.pojo.GalleryImages;
import com.rohitsavant.gorentjoy.pojo.Listings;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 20/06/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder>{

ArrayList<GalleryImages> images;
ArrayList<String> photoUrl;

Context mContext;

    public GalleryAdapter(ArrayList<GalleryImages> images,ArrayList<String> photoUrl, Context mContext) {
        this.images = images;
        this.mContext = mContext;
        this.photoUrl=photoUrl;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {

      /*  if(images.size()==0)
        {
            ListingFragment.mLinemptyList.setVisibility(View.VISIBLE);
        }
        else{
            ListingFragment.mLinemptyList.setVisibility(View.INVISIBLE);
        }*/
        return images.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView mImgThumb;

        GalleryImages mItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImgThumb=itemView.findViewById(R.id.img_gallery);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position= (int) mImgThumb.getTag();
                    Activity activity = (Activity) mContext;

                    ZGallery.with(activity,photoUrl)
                            .setSelectedImgPosition(position)
                            .setToolbarTitleColor(ZColor.WHITE)
                            //.setTitle("Hello")
                            //.setToolbarColorResId(activity.getResources().getColor(R.color.red_600))
                            .show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        GalleryImages image = images.get(position);
        holder.mItem = images.get(position);

        holder.mImgThumb.setTag(position);

        Picasso.with(mContext)
                .load(image.getThumb())
                .fit()
                .into(holder.mImgThumb);

    }

}
