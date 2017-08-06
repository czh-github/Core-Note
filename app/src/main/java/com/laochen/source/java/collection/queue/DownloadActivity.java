package com.laochen.source.java.collection.queue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.laochen.jni.R;

import java.util.Timer;

public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = "DownloadActivity";

    private ListView mListView;
    private ImageView mImageView;
    private String[] texts = new String[1000];
    VelocityTracker velocityTracker;
    private int mTouchSlop;
    private String mDir;
    private volatile int mFirstVisibleItem = 0;
    private volatile boolean mRunning;
    private boolean mScrolling = false;
    private int mScrollState = -1;
    private long mPreFirstVisibleItemTime = System.currentTimeMillis();

    private int mYScrollVelocity = 0;
    private int mYFlingVelocity = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Bitmap bitmap = (Bitmap) msg.obj;
            mImageView.setImageBitmap(bitmap);
        }
    };

    private Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initData();
        mListView = (ListView) findViewById(R.id.listview);
        mImageView = (ImageView) findViewById(R.id.imageview);
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        mListView.setAdapter(new SimpleAdapter());

        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        // 每次抬手需要执行对应下载
                        download(System.currentTimeMillis(), ImageUrls.URLS[mFirstVisibleItem], mDir, mFirstVisibleItem + ".jpg");
                        break;
                }
//                setYScrollVelocity(event);
                return false;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;
                switch(scrollState) {
                    case SCROLL_STATE_IDLE:
                        //滑动停止时调用
                        Log.e(TAG, "IDLE");
                        // 加载firstVisibleItem及其周边的图片
                        mScrolling = false;
                        download(System.currentTimeMillis(), ImageUrls.URLS[mFirstVisibleItem], mDir, mFirstVisibleItem + ".jpg");
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        //正在滚动时调用
                        Log.e(TAG, "TOUCH_SCROLL");
                        break;
                    case SCROLL_STATE_FLING:
                        //手指快速滑动时,在离开ListView由于惯性滑动
                        Log.e(TAG, "FLING");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mScrolling = true;
                if (mFirstVisibleItem != firstVisibleItem) {
                    displayImage(firstVisibleItem);

//                    final int deltaItemCount = firstVisibleItem - mFirstVisibleItem;
//                    final long currTime = System.currentTimeMillis();
//                    final long deltaTime = currTime - mPreFirstVisibleItemTime;
//                    final double speed = 1000.0 * deltaItemCount / deltaTime;
//                    final String fileName = firstVisibleItem + ".jpg";
//                    Log.e(TAG, firstVisibleItem + "th item of ListView scroll speed(item/s):" + speed);
//                    if (Math.abs(speed) <= 20) {
//                        download(currTime, ImageUrls.URLS[firstVisibleItem], mDir, fileName);
//                    }
                    mFirstVisibleItem = firstVisibleItem;
//                    mPreFirstVisibleItemTime = currTime;
                }
            }
        });
    }

    private void download(long time, String url, String dir, String name) {
        if (!Utils.isFileExists(dir, name) && !ThumbnailManager2.containsTaskId(url)) {
            produce(new ThumbnailDownloadTask(time, url, dir, name));
        }
    }



    /**
     * 竖直方向scroll速度(px/s)
     */
    private void setYScrollVelocity(MotionEvent event) {
        velocityTracker.addMovement(event);
        // 计算速度：指定1000得到的速度单位是像素/1000ms
        velocityTracker.computeCurrentVelocity(1000);
        // 获取X和Y方向的速度，可以是负数
        int vx = (int) velocityTracker.getXVelocity();
        int vy = (int) velocityTracker.getYVelocity();
//                Log.e(TAG, "VelocityX:" + vx + "px/s");
        Log.e(TAG, "VelocityY:" + vy + "px/s");
        mYScrollVelocity = vy;
    }

    /**
     * 竖直方向fling速度
     */
    private void getYFlingVelocity() {
        // TODO 需要自己计算
        mYFlingVelocity = 0;
    }

    private void initData() {
        mDir = Utils.createFileDir(this, "0Thumbnails").getAbsolutePath();
        for (int i = 0; i < 1000; i++) {
            texts[i] = "" + i;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        velocityTracker = VelocityTracker.obtain();
        mRunning = true;
        registerDownloadResultReceiver();
        consume();
    }

    @Override
    protected void onPause() {
        mRunning = false;
        super.onPause();
//        velocityTracker.recycle();
        unregisterDownloadResultReceiver();
    }

    @Override
    protected void onDestroy() {
        ThumbnailManager2.clear();
        super.onDestroy();
    }

    /**
     * 往队列里添加任务
     */
    private void produce(ThumbnailDownloadTask task) {
        ThumbnailManager2.offer(task);
    }

//    private void produceAll(int start, int end) {
//       for (int i = start; i < end; i++) {
//           final String name = i + ".jpg";
//           if (!Utils.isFileExists(mDir, name)) {
//               ThumbnailDownloadTask task = new ThumbnailDownloadTask(System.currentTimeMillis(), ImageUrls.URLS[i], mDir, name);
//               produce(task);
//           }
//       }
//    }

    /**
     * 从队列中取任务执行
     */
    private void consume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunning) {
                    ThumbnailManager2.execute(ThumbnailManager2.poll());
                }
            }
        }).start();
    }

    class SimpleAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public Object getItem(int position) {
            return texts[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(DownloadActivity.this).inflate(R.layout.item, parent, false);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(texts[position]);
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView textView;
    }


    private void displayImage(int number) {
        Bitmap bitmap = BitmapLruCache.get().getBitmapFromMemoryCache(ImageUrls.URLS[number]);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            final String imageName = String.valueOf(number) + ".jpg";
            bitmap = Utils.getBitmapFromFile(mDir, imageName);
            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    class DownloadResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String url = intent.getStringExtra("url");
            final int tag = intent.getIntExtra("tag", AsyncImageLoaderListener.FAILED);
            final Bitmap bitmap = intent.getParcelableExtra("bitmap");

            if (tag == AsyncImageLoaderListener.SUCCESS && bitmap != null) {
//                if (url.equals(ImageUrls.URLS[mFirstVisibleItem])) {
                    mImageView.setImageBitmap(bitmap);
//                }
            }

            ThumbnailManager2.removeIdFromList(url);
        }
    }
    private DownloadResultReceiver mReceiver;

    private void registerDownloadResultReceiver() {
        if (mReceiver == null) {
            mReceiver = new DownloadResultReceiver();
            IntentFilter filter = new IntentFilter(ThumbnailManager2.ACTION_SEND_DOWNLOAD_RESULT);
            registerReceiver(mReceiver, filter);
        }
    }

    private void unregisterDownloadResultReceiver() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

}
