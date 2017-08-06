package com.laochen.source.view.dispatcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Date:2017/6/30 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class EventView extends View {
    public static final String TAG = "EventView";

    public EventView(Context context) {
        super(context);
        setClickable(true);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = super.dispatchTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ChildView#dispatchTouchEvent#ACTION_DOWN:" + ret);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ChildView#dispatchTouchEvent#ACTION_MOVE:" + ret);
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ChildView#dispatchTouchEvent#ACTION_UP:" + ret);
                break;
        }
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
/*        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ChildView#onTouchEvent#ACTION_DOWN");
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ChildView#onTouchEvent#ACTION_MOVE");
                return false;
//                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ChildView#onTouchEvent#ACTION_UP");
                break;
        }*/
         boolean ret = super.onTouchEvent(event);
        Log.e(TAG, "ChildView#onTouchEvent:" + ret);
        return ret;
    }
}
