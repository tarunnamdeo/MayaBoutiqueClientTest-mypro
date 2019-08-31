package com.example.mayaboutiqueclient;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mayaboutiqueclient.pojo.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullPageAdapter extends PagerAdapter {
    private Activity activity;
    private ArrayList<Post> image_Paths;
    private LayoutInflater inflater;
    private int postionimg;

    public FullPageAdapter(Activity activity, ArrayList<Post> image_Paths, int position) {
        this.activity = activity;
        this.image_Paths = image_Paths;
        this.postionimg=position;
    }

    @Override
    public int getCount() {
        return image_Paths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imgDisplay;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height=displayMetrics.heightPixels;
        int width=displayMetrics.widthPixels;
        imgDisplay.setMinimumHeight(height);
        imgDisplay.setMinimumWidth(width);

       try {

           if (position==postionimg){
               Log.d("posds","haha");
           }
           Picasso.get()
                   .load(image_Paths.get(position).getPost_image())
                   .placeholder(R.mipmap.ic_launcher)
                   .error(R.mipmap.ic_launcher)
                   .into(imgDisplay);
       }catch (Exception e){

       }

       container.addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);

    }
}
