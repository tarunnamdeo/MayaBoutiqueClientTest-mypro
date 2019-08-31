package com.example.mayaboutiqueclient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mayaboutiqueclient.pojo.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class KurtiAdapter extends PagerAdapter {

    private ArrayList<Post> models;
    private LayoutInflater layoutInflater;
    private Context context;
    public Palette.Swatch lightVibrent;
//    private ArrayList<View> views = new ArrayList<View>();

    public KurtiAdapter(ArrayList<Post> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (models != null) {
            return models.size();
        } else {
            return 1;
        }

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.itemss, container, false);

        ImageView imageView;
        TextView title, desc, date;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);
        date = view.findViewById(R.id.date);

        //imageView.setImageResource(Integer.parseInt(models.get(position).getPost_image()));
        title.setText(models.get(position).getTime());
        desc.setText(models.get(position).getDesc_image());
        date.setText(models.get(position).getDate());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,FullScreenImage.class);


                intent.putExtra("mylist",models);
                intent.putExtra("pos",position);
                intent.putExtra("image_path",models.get(position).getPost_image());

                context.startActivity(intent);
            }
        });
        try {
            Picasso.get()
                    .load(models.get(position).getPost_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        } catch (Exception e) {

        }

//        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
//        if (bitmap!=null){
//
//            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                @Override
//                public void onGenerated(@Nullable Palette palette) {
//                    lightVibrent=palette.getDominantSwatch();
//
//                    if (lightVibrent!=null) {
//                        int s=lightVibrent.getTitleTextColor();
//                        int b=lightVibrent.getBodyTextColor();
//                        int rgb=lightVibrent.getRgb();
//
//                        view.setBackgroundColor(rgb);
//
//                    }
//
//                }
//            });
//
//        }

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // Intent intent = new Intent(context, DetailActivity.class);
//               // intent.putExtra("param", models.get(position).getTitle());
//               // context.startActivity(intent);
//                // finish();
//            }
//        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //return super.getItemPosition(object);
        int index = models.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }
}
