package com.laochen.source.android.widget.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.laochen.jni.R;
import com.laochen.source.java.collection.queue.ImageUrls;

import java.util.ArrayList;

public class RecyclerViewExampleActivity extends AppCompatActivity implements OnReceivedNewPhoto {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    private ArrayList<Photo> mPhotosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_example);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        initData();
        setRecyclerViewScrollListener();
        setRecyclerViewItemTouchListener();
    }

    private void initData() {
        mPhotosList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mPhotosList.add(new Photo(ImageUrls.URLS[i], "2017/8/4", String.valueOf(i)));
        }
        mAdapter = new RecyclerAdapter(mPhotosList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPhotosList.size() == 0) {
            requestPhoto();
        }
    }

    private int getLastVisibleItemPosition() {
        return mLinearLayoutManager.findLastVisibleItemPosition();
    }

    private void setRecyclerViewScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
//                if (!mImageRequester.isLoadingData() && totalItemCount == getLastVisibleItemPosition() + 1) {
//                    requestPhoto();
//                }
            }
        });
    }

    private void setRecyclerViewItemTouchListener() {

        //1 You create the callback and tell it what events to listen for. It takes two parameters, one for drag directions and one for swipe directions,
        // but you’re only interested in swipe, so you pass 0 to inform the callback not to respond to drag events.
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                //2 You return false in onMove because you don’t want to perform any special behavior here
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //3 onSwiped is called when you swipe an item in the direction specified in the ItemTouchHelper.
                // Here, you request the viewHolder parameter passed for the position of the item view,
                // then you remove that item from your list of photos.
                // Finally, you inform the RecyclerView adapter that an item has been removed at a specific position.
                int position = viewHolder.getAdapterPosition();
                mPhotosList.remove(position);
                mRecyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        //4 You initialize the ItemTouchHelper with the callback behavior you defined, and then attach it to the RecyclerView.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void requestPhoto() {

    }

    @Override
    public void receivedNewPhoto(final Photo newPhoto) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPhotosList.add(newPhoto);
                    mAdapter.notifyItemInserted(mPhotosList.size() - 1);
                }
        });
    }
}
