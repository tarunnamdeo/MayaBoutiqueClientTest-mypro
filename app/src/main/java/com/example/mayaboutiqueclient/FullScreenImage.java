package com.example.mayaboutiqueclient;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.example.mayaboutiqueclient.pojo.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenImage extends AppCompatActivity {

    private ViewPager viewPager;
    private FullPageAdapter adapter;
    private TouchImageView imageView;
    boolean isImageFitToScreen;
    public Palette.Swatch lightVibrent;
    private Bitmap bitmap;
    private LinearLayout linearLayout;
    private boolean state = true;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_adapter);
        imageView = findViewById(R.id.image_post);

        linearLayout = findViewById(R.id.layoutimage);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            View decoorview = getWindow().getDecorView();
//
//            decoorview.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            );
//        }

        Toast.makeText(this, "This is Full Image", Toast.LENGTH_SHORT).show();
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {


//                hidingthebar();


//                if (isImageFitToScreen) {
//                    isImageFitToScreen = false;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    imageView.setAdjustViewBounds(true);
//                } else {
//                    isImageFitToScreen = true;
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });


        ArrayList<Post> myList = (ArrayList<Post>) getIntent().getSerializableExtra("mylist");
        int position = (int) getIntent().getIntExtra("pos", 0);
        String image_post = getIntent().getStringExtra("image_path");
        System.out.println("ps" + position);
        Log.d("arraya", String.valueOf(myList));

        Log.d("imagge", image_post);

        try {
            Picasso.get()
                    .load(myList.get(position).getPost_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (Exception e) {

        }


        if (bitmap != null) {

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    lightVibrent = palette.getDominantSwatch();

                    if (lightVibrent != null) {
                        int s = lightVibrent.getTitleTextColor();
                        int b = lightVibrent.getBodyTextColor();
                        int rgb = lightVibrent.getRgb();

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setDisplayShowHomeEnabled(true);
                            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(rgb));

                            Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
                            window.setStatusBarColor(rgb);
                        }

                        linearLayout.setBackgroundColor(rgb);
                        // view.setBackgroundColor(rgb);

                    }

                }
            });

        }


        // viewPager = findViewById(R.id.pager);
        // viewPager.setCurrentItem(position);
        //   adapter = new FullPageAdapter(this, myList,position);
        //viewPager.setAdapter(adapter);
    }

    private void hidingthebar() {


        if (state == false) {
            Log.d("state", "false");
            state = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                View decoorview = getWindow().getDecorView();

                decoorview.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
            }


        } else {
            state = false;
            Log.d("state", "true");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                View decoorview = getWindow().getDecorView();

                decoorview.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            View decoorview = getWindow().getDecorView();
//
//            decoorview.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            );
//        }

//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
//            imageView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
//
//        }
//        else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
//            imageView.setSystemUiVisibility( View.STATUS_BAR_HIDDEN );
//        else{}

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scalefactor = detector.getScaleFactor();
            scalefactor = Math.max(0.3f, Math.min(scalefactor, 9.0f));

            matrix.setScale(scalefactor, scalefactor);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
