package com.laochen.jni.view.dispatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.laochen.jni.R;

public class EventActivity extends AppCompatActivity {
    private EventView mEventView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mEventView = (EventView) findViewById(R.id.eventView);

        mEventView.setOnTouchListener(new View.OnTouchListener() {
            // 如果onTouch返回true，那么该事件不会传递给onTouchEvent()；否则会传递。
            // onTouch返回true表示该Listener消费了该事件；否则不消费。
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e(EventView.TAG, "onTouch#ACTION_DOWN");
//                        return false;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e(EventView.TAG, "onTouch#ACTION_MOVE");
//                        return false;
//                    case MotionEvent.ACTION_UP:
//                        Log.e(EventView.TAG, "onTouch#ACTION_UP");
//                        return false;
//                }
                return false;
            }
        });

        mEventView.setOnClickListener(new View.OnClickListener() {
            // onClick方法在onTouchEvent()中被调用
            @Override
            public void onClick(View v) {
                Log.e(EventView.TAG, "onClick");
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(EventView.TAG, "Activity#onTouchEvent#ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(EventView.TAG, "Activity#onTouchEvent#ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(EventView.TAG, "Activity#onTouchEvent#ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
