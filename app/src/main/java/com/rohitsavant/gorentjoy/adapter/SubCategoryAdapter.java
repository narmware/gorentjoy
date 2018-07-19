package com.rohitsavant.gorentjoy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.rohitsavant.gorentjoy.R;
import com.rohitsavant.gorentjoy.activity.NavigationMainActivity;
import com.rohitsavant.gorentjoy.fragment.ListingFragment;
import com.rohitsavant.gorentjoy.pojo.Category;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 20/06/18.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>{

ArrayList<Category> categories;
Context mContext;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public SubCategoryAdapter(ArrayList<Category> categories, Context mContext,FragmentManager fragmentManager) {
        this.categories = categories;
        this.mContext = mContext;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.sub_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTxtCatTitle;
        TextView mTxtCount;
        Category mItem;

        public MyViewHolder(View view) {
            super(view);

           mTxtCatTitle=view.findViewById(R.id.txt_sub_title);
            mTxtCount=view.findViewById(R.id.txt_count);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setFragment(ListingFragment.newInstance(mItem.getId_category(),null));
                    NavigationMainActivity.mTxtTitle.setText(mItem.getName());

                    //Toast.makeText(mContext,mItem.getName()+" : "+mItem.getId_category_parent(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category=categories.get(position);

        holder.mTxtCatTitle.setText(category.getName());
        holder.mTxtCount.setText(category.getId_category());
        holder.mItem=category;
    }

    public void setFragment(Fragment fragment)
    {
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
