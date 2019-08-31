package com.example.mayaboutiqueclient.fragment.client;

import android.content.Context;
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


public class HomeFragment extends Fragment {
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


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_blouse, container, false);

        viewPager = mMainView.findViewById(R.id.viewPager);
        progressBar = mMainView.findViewById(R.id.progressBar2);
        viewPager.setPadding(130, 0, 130, 0);

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
//                   // progressBar.setVisibility(View.VISIBLE);
//                    mCurrentPage++;
//                    itemPos = 0;
//                  //  loadMoreMessages();
//                    Toast.makeText(getContext(), "Last", Toast.LENGTH_SHORT).show();
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
                .getInstance().getReference().child("Users").child(mCurrent_user_id).child("Post").child("HOME");
        messageRef.keepSynced(true);
        Query messageQuery = messageRef.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);
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

//                itemPos++;
//
//                if (itemPos == 1) {
//
//                    String messageKey = dataSnapshot.getKey();
//
//                    mLastKey = messageKey;
//                    mPrevKey = messageKey;
//
//                }


                mArray.add(post);
//                adapterCard.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

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

                adapter = new KurtiAdapter(mArray, getContext());
                adapter.notifyDataSetChanged();
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(5);


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

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                progressBar.setVisibility(View.VISIBLE);

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

                //Collections.reverse(mArray);
                adapter.notifyDataSetChanged();
                //viewPager.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
               // adapter.instantiateItem(viewPager,20);

                // mLinearLayout
                //  linearLayoutManager.scrollToPositionWithOffset(10, 0);

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

       // viewPager.invalidate();
       // viewPager.setAdapter(adapter);
        //progressBar.setVisibility(View.INVISIBLE);
    }

}
