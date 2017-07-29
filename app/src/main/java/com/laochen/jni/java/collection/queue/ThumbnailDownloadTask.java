package com.laochen.jni.java.collection.queue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Date:2017/7/10 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ThumbnailDownloadTask implements Runnable {
    private static final String TAG = "ThumbnailDownloadTask";
    private long mTime; // HHmmss
    private String mUrl; // 下载地址
    private String mPath; // 保存目录
    private String mName; // 保存文件名
    private AsyncImageLoaderListener mListener;

    private static final int WIDTH = 320;
    private static final int HEIGHT = 180;

    public ThumbnailDownloadTask(long time, String url, String dir, String name/*, Handler handler*/) {
        this.mTime = time;
        this.mUrl = url;
        this.mPath = dir;
        this.mName = name;
//        this.mHandler = handler;
    }

    public long getTime() {
        return mTime;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setAsyncImageLoaderListener(AsyncImageLoaderListener listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        loadAndSaveImage(mUrl, WIDTH, HEIGHT);
        long endTime = System.currentTimeMillis();
        Log.e("ThumbnailDownloadTask", mName + "download spend time:" + (endTime - startTime));
    }

    private void loadAndSaveImage(final String url, final int width, final int height) {
        int tag;
        Bitmap bitmap = downloadImage(url, width, height);
        if (bitmap == null) {
            // TODO 下载图片失败
            tag = AsyncImageLoaderListener.FAILED;
            Log.e(TAG, mName + "下载失败:" + url);
        } else {
            // 下载图片成功，加入到内存缓存和磁盘缓存
            tag = AsyncImageLoaderListener.SUCCESS;
            BitmapLruCache.get().addBitmapToMemoryCache(url, bitmap);
            Utils.saveBitmap(mPath, mName, bitmap);
            Log.e(TAG, mName + "下载成功:" + url);
        }
        if (mListener != null) {
            mListener.onAsyncImageLoader(url, tag, bitmap);
        }
    }

    /**
     * 下载图片
     * @param url 待下载图片的url
     * @param width 把下载的图片压缩成指定宽
     * @param height 把下载的图片压缩成指定高
     * @return 压缩后的Bitmap，下载失败返回null
     */
    private Bitmap downloadImage(final String url, final int width, final int height){
        Bitmap bitmap = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5L, TimeUnit.SECONDS) // 连接一个url的等待时间
                .writeTimeout(5L, TimeUnit.SECONDS) // server读取client发送请求的时间
                .readTimeout(5L, TimeUnit.SECONDS) // 等待server返回response的时间
                .build();
        Request request = new Request.Builder().url(url).build();
        // TODO 设置超时时间
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] byteIn = response.body().bytes();
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length, bmpFactoryOptions);
                int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / height);
                int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / width);
                if (heightRatio > 1 && widthRatio > 1) {
                    bmpFactoryOptions.inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
                }
                bmpFactoryOptions.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length, bmpFactoryOptions);
            }
        } catch (IOException ioe) {
            // TODO 下载失败
            return null;
        }
        return bitmap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ThumbnailDownloadTask task = (ThumbnailDownloadTask) obj;
        return TextUtils.equals(this.mUrl, task.mUrl);
    }

    @Override
    public String toString() {
        return "Thumbnail#" + mTime + "-" + mUrl;
    }

}
