package com.laochen.jni.view.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.laochen.jni.R;

public class ScrollViewActivity extends AppCompatActivity {
    private View mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        mScrollView = findViewById(R.id.scrollView);
        mScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scrollView();
                ((ScrollView)mScrollView).smoothScrollTo(600, 600);
            }
        });
    }

    // 通过改变View的LayoutParams实现View滑动
    private void scrollView() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mScrollView.getLayoutParams();
        params.leftMargin += 100;
        params.topMargin += 100;
        mScrollView.setLayoutParams(params);
    }
}
