package com.laochen.source.java.collection.queue;

import android.graphics.Bitmap;

public interface AsyncImageLoaderListener {
    int SUCCESS = 0;
    int FAILED = -1;

    /**
     * @param url 图片的url
     * @param tag 标识下载结果
     * @param bitmap 若下载成功，即为下载的图片
     */
    void onAsyncImageLoader(String url, int tag, Bitmap bitmap);
}