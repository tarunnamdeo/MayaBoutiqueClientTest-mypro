package com.example.mayaboutiqueclient.fragment.client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mayaboutiqueclient.KurtiAdapter;
import com.example.mayaboutiqueclient.R;
import com.example.mayaboutiqueclient.pojo.Post;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;


public class BlouseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private ProgressBar progressBar;
    //private cloths1.OnFragmentInteractionListener mListener;
    private int currentItems, totalItems, scrolledOutItems;
    private View mMainView;
    ViewPager viewPager;
    private String mCurrent_user_id;

    private String mLastKey = "";
    private String mPrevKey = "";
    ArrayList<Post> mArray;
    KurtiAdapter adapter;

  //  private FirebaseAuth mAuth;


    public BlouseFragment() {
        // Required empty public constructor
    }

    public static BlouseFragment newInstance(String param1, String param2) {
        BlouseFragment fragment = new BlouseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_blouse, container, false);

        viewPager = mMainView.findViewById(R.id.viewPager);
        progressBar = mMainView.findViewById(R.id.progressBar2);
        //mAuth = FirebaseAuth.getInstance();
        viewPager.setPadding(130,0,130,0);

        if (!isConnected(getContext())){
            Toast.makeText(getContext(), "NO INTERNET", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }else {
            progressBar.setVisibility(View.VISIBLE);


        }
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//                currentItems=viewPager.getCurrentItem();
//                //totalItems=viewPager.getChildCount();
//                totalItems=adapter.getCount();
//
//                if (i!=totalItems-1){
//
//                }else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    mCurrentPage++;
//                    itemPos = 0;
//                    loadMoreMessages();
//                    Snackbar.make(mMainView, "Last Item Please Wait", Snackbar.LENGTH_SHORT)
//                            .setAction("Action", null).show();
//                    //Toast.makeText(getContext(), "Last", Toast.LENGTH_SHORT).show();
//                }
//
//
//                Log.d("current", String.valueOf(currentItems));
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        });

        mCurrent_user_id = "cowAdgQdCrNcit2TU8hbQ79RQd03";

        DatabaseReference messageRef = FirebaseDatabase
                .getInstance().getReference().child("Users").child(mCurrent_user_id).child("Post").child("BLOUSE");
        messageRef.keepSynced(true);
       // Query messageQuery = messageRef.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);
        myfunctionload(messageRef);
        return mMainView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }




    private void myfunctionload(Query messageQuery) {
        mArray = new ArrayList<Post>();

        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id);
        mUserDatabase.keepSynced(true);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);

                itemPos++;

                if (itemPos == 1) {

                    String messageKey = dataSnapshot.getKey();

                    mLastKey = messageKey;
                    mPrevKey = messageKey;

                }


                mArray.add(post);
//                adapterCard.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

                //   mRecycler.scrollToPosition(mArray.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();


                 Collections.reverse(mArray);

                adapter=new KurtiAdapter(mArray,getContext());
              //  adapterCard = new AdapterCard(mArray, thumb_image, name, this, getContext());
                adapter.notifyDataSetChanged();
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(5);
              //  mRecycler.setAdapter(adapterCard);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void loadMoreMessages() {

        DatabaseReference messageRef = FirebaseDatabase
                .getInstance().getReference().child("Users").child(mCurrent_user_id).child("Post").child("BLOUSE");
        messageRef.keepSynced(true);

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Post message = dataSnapshot.getValue(Post.class);
                String messageKey = dataSnapshot.getKey();

                if (!mPrevKey.equals(messageKey)) {

                    mArray.add(itemPos++, message);

                } else {

                    mPrevKey = mLastKey;
                    progressBar.setVisibility(View.INVISIBLE);

                }


                if (itemPos == 1) {

                    mLastKey = messageKey;

                }


                Log.d("TOTALKEYS", "Last Key : " + mLastKey + " | Prev Key : " + mPrevKey + " | Message Key : " + messageKey);

                 //Collections.reverse(mArray);
                adapter.notifyDataSetChanged();
               // viewPager.invalidate();
                progressBar.setVisibility(View.INVISIBLE);


                // mLinearLayout
                //   linearLayoutManager.scrollToPositionWithOffset(10, 0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressBar.setVisibility(View.INVISIBLE);

    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null & wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

}
