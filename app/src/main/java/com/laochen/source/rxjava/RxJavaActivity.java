package com.laochen.source.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laochen.jni.R;

public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        RxJavaDemo.scheduler();
    }
}
