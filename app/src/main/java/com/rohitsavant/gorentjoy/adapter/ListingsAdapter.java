package com.rohitsavant.gorentjoy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rohitsavant.gorentjoy.MyApplication;
import com.rohitsavant.gorentjoy.R;
import com.rohitsavant.gorentjoy.fragment.ListingFragment;
import com.rohitsavant.gorentjoy.fragment.SingleAdFragment;
import com.rohitsavant.gorentjoy.pojo.Category;
import com.rohitsavant.gorentjoy.pojo.CategoryResponse;
import com.rohitsavant.gorentjoy.pojo.Listings;
import com.rohitsavant.gorentjoy.support.SupportFunctions;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rohitsavant on 20/06/18.
 */

public class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.MyViewHolder>{

ArrayList<Listings> listings;
Context mContext;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public ListingsAdapter(ArrayList<Listings> listings, Context mContext, FragmentManager fragmentManager) {
        this.listings = listings;
        this.mContext = mContext;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_item, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {

        if(listings.size()==0)
        {
            ListingFragment.mLinemptyList.setVisibility(View.VISIBLE);
        }
        else{
            ListingFragment.mLinemptyList.setVisibility(View.INVISIBLE);
        }
        return listings.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTxTitle,mTxtPublishDate,mTxtPrice,mTxtMore;
        TextView mTxtDesc,mTxtDeposite,mTxtDuration;

        ImageView mImgThumb;
        ExpandableLayout expandableLayout;

        Listings mItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTxTitle=itemView.findViewById(R.id.txt_title);
            mTxtPrice=itemView.findViewById(R.id.txt_price);
            mTxtPublishDate=itemView.findViewById(R.id.txt_publish_date);
            mImgThumb=itemView.findViewById(R.id.img_product);

            mTxtMore=itemView.findViewById(R.id.txt_more);
            expandableLayout=itemView.findViewById(R.id.expandable_layout);

            mTxtDesc=itemView.findViewById(R.id.txt_desc);
            mTxtDeposite=itemView.findViewById(R.id.txt_sec_deposite);
            mTxtDuration=itemView.findViewById(R.id.txt_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(SingleAdFragment.newInstance(mItem.getId_ad(),""));
                    Toast.makeText(mContext,mItem.getId_ad(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setFragment(Fragment fragment)
    {
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Listings listing = listings.get(position);
        holder.mItem = listings.get(position);

        holder.mTxTitle.setText(listing.getTitle());

        String newPrice=listing.getPrice().replace("&#2352;","Rs.");

        holder.mTxtPrice.setText("Price: "+newPrice);
        holder.mTxtPublishDate.setText("Pubished Date: "+listing.getPublished());

        holder.mTxtDuration.setText("Minimum duration of Rent: "+listing.getCf_plan());
        holder.mTxtDeposite.setText("Security Deposit: "+listing.getCf_security());
        holder.mTxtDesc.setText(listing.getDescription());

        holder.mTxtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.expandableLayout.isExpanded()==true)
                {
                    holder.expandableLayout.collapse();
                }
                else{
                    holder.expandableLayout.expand();
                }
            }
        });

        Picasso.with(mContext)
                .load(listing.getThumb())
                .fit()
                .into(holder.mImgThumb);

        holder.expandableLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {

                /*if(state==3)
                {
                    Toast.makeText(mContext,state+"On",Toast.LENGTH_SHORT).show();
                }
                if(state==0)
                {
                    Toast.makeText(mContext,state+"OFF",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

}
