package com.laochen.source.android.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laochen.jni.R;

/**
 * 介绍{@link android.os.Parcelable}接口
 * <p>
 * 1.是什么？
 * 意为"可打包的"。
 * <p>
 * 2.为什么引入？
 * <p>
 * 3.怎样使用？
 * <p>
 * 4.跟{@link java.io.Serializable}比较
 */
public class ParcelableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);
    }
}
