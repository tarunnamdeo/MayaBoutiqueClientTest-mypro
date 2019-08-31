package com.example.mayaboutiqueclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayaboutiqueclient.pojo.Post;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {

    ArrayList<Post> Data;
    String thumb_image, name;
    Context context;
    View v;
    ArrayList<String> arrayList;

    public AdapterCard(ArrayList<Post> mArray, String thumb_image, String name, ValueEventListener valueEventListener, Context applicationContext) {
        this.Data = mArray;
        this.thumb_image = thumb_image;
        this.name = name;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_litems, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.displayName.setText(name);
        viewHolder.displaytime.setText(Data.get(i).getTime());
        viewHolder.image_desc.setText(Data.get(i).getDesc_image());
        viewHolder.date1.setText(Data.get(i).getDate());


        Picasso.get().load(thumb_image).networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.common_full_open_on_phone).into(viewHolder.profileImage, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

                Picasso.get().load(thumb_image).placeholder(R.drawable.common_full_open_on_phone).into(viewHolder.profileImage);

            }
        });
        Picasso.get().load(Data.get(i).getPost_image()).networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_launcher_foreground).into(viewHolder.postImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }


        });


        //  Uri selectedImage = Uri.parse(Data.get(i).getPost_image());
        //Bitmap bitmap = ImageUtils.decodeBitmap(this, selectedImage);
        // setImage(bitmap);
        // File file=new File(context.getFilesDir(),Data.get(i).getPost_image());
//        Bitmap bitmap=((BitmapDrawable)viewHolder.postImage.getDrawable()).getBitmap();
//        if (bitmap!=null){
//
//            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                @Override
//                public void onGenerated(@Nullable Palette palette) {
//                    viewHolder.lightVibrent=palette.getLightVibrantSwatch();
//
//                    if (viewHolder.lightVibrent!=null) {
//                        int s=viewHolder.lightVibrent.getTitleTextColor();
//                        int b=viewHolder.lightVibrent.getBodyTextColor();
//                        int rgb=viewHolder.lightVibrent.getRgb();
//
//                        viewHolder.displayName.setTextColor(s);
//                        viewHolder.date1.setTextColor(s);
//                        viewHolder.displaytime.setTextColor(s);
//                        viewHolder.image_desc.setTextColor(b);
//                        v.setBackgroundColor(rgb);
//
//                    }
//
//                }
//            });

    //}

        arrayList=new ArrayList<>();
        int l=Data.size();
        for (int j=0;j<l;j++){
            arrayList.add(Data.get(j).getPost_image());
        }
        System.out.println(arrayList);

        viewHolder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,FullScreenImage.class);


                intent.putExtra("mylist", arrayList);
                intent.putExtra("pos",i);

                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView image_desc;
        public ImageView profileImage;
        public TextView displayName;
        public ImageView postImage;
        public TextView displaytime;
        public TextView date1;
        public Palette.Swatch lightVibrent;
        public ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_desc=(TextView)itemView.findViewById(R.id.cardTitle);
            displayName=(TextView)itemView.findViewById(R.id.textid);
            displaytime=(TextView)itemView.findViewById(R.id.texttime);
            postImage=(ImageView)itemView.findViewById(R.id.cardImage);
            profileImage=(ImageView)itemView.findViewById(R.id.imageView);
            date1=(TextView)itemView.findViewById(R.id.date1);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progressBar3);
        }
    }



}
