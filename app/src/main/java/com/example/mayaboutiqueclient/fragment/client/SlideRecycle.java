package com.example.mayaboutiqueclient.fragment.client;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mayaboutiqueclient.AdapterCard;
import com.example.mayaboutiqueclient.R;
import com.example.mayaboutiqueclient.pojo.Post;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideRecycle extends Fragment {

    private static final int TOTAL_ITEMS_TO_LOAD = 2;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private ProgressBar progressBar;
    private int currentItems, totalItems, scrolledOutItems;

    private String mLastKey = "";
    private String mPrevKey = "";

    private RecyclerView mRecycler;
    private View mMainView;

    private Boolean isScrolling = false;
    private LinearLayoutManager linearLayoutManager;
    private String mCurrent_user_id;
    ArrayList<Post> mArray;
    private AdapterCard adapterCard;

    public SlideRecycle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_slide_recycle, container, false);
        mRecycler = mMainView.findViewById(R.id.sliderecycler);
        progressBar = mMainView.findViewById(R.id.progressBar2);

        mCurrent_user_id = "cowAdgQdCrNcit2TU8hbQ79RQd03";
        DatabaseReference messageRef = FirebaseDatabase
                .getInstance().getReference().child("Users").child(mCurrent_user_id).child("Post").child("HOME");
        messageRef.keepSynced(true);
        Query messageQuery = messageRef.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        //linearLayoutManager.setStackFromEnd(true);

        mRecycler.setLayoutManager(linearLayoutManager);

//        linearLayoutManager.setStackFromEnd(true);

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrolledOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                Log.d("child", String.valueOf(currentItems) + String.valueOf(totalItems) + String.valueOf(scrolledOutItems));
                Log.d("childw", String.valueOf(currentItems + scrolledOutItems == totalItems));
                if (isScrolling &&  totalItems == (scrolledOutItems + currentItems)) {
                        //currentItems == 1 && scrolledOutItems == 0) {
//
                    System.out.println("pass");
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Last", Toast.LENGTH_SHORT).show();

                    // mRefreshLayout.setRefreshing(true);
                    mCurrentPage++;
                    //Log.d("page",String.valueOf(mCurrentPage));

                    itemPos = 0;

                    loadMoreMessages();
                }

            }
        });

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecycler);


        myfunctionload(messageRef);
        return mMainView;


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

                //Collections.reverse(mArray);

                mArray.add(post);
//                adapterCard.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);

                //mRecycler.scrollToPosition(mArray.size()-1);

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
                Log.d("itemsss",String.valueOf(mArray.size()));

                adapterCard = new AdapterCard(mArray, thumb_image, name, this, getContext());

                mRecycler.setAdapter(adapterCard);
                adapterCard.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void loadMoreMessages() {

        DatabaseReference messageRef = FirebaseDatabase
                .getInstance().getReference().child("Users").child(mCurrent_user_id).child("Post").child("HOME");
        messageRef.keepSynced(true);

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(2);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Post message = dataSnapshot.getValue(Post.class);
                String messageKey = dataSnapshot.getKey();

                if (!mPrevKey.equals(messageKey)) {

                    mArray.add(itemPos++, message);

                } else {

                    mPrevKey = mLastKey;

                }


                if (itemPos == 1) {

                    mLastKey = messageKey;

                }


                Log.d("TOTALKEYS", "Last Key : " + mLastKey + " | Prev Key : " + mPrevKey + " | Message Key : " + messageKey);

               // Collections.reverse(mArray);
                adapterCard.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                //    mRefreshLayout.setRefreshing(false);

                // mLinearLayout
                    //linearLayoutManager.scrollToPositionWithOffset(10, 0);

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

    }

}
