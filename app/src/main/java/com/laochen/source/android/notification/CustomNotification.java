package com.laochen.source.android.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.laochen.jni.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Date:2017/9/8 <p>
 * Author:chenzehao@danale.com <p>
 * Description:自定义通知，兼容系统通知
 */

public class CustomNotification {
    private static final String TAG = "CustomNotification";
    private static final double COLOR_THRESHOLD = 180.0;
    private static final String ACTION_CUSTOM_NOTIFICATION = "ACTION_CUSTOM_NOTIFICATION";

    private int mTitleColor;
    private int mTextColor;
    private Drawable mBgDrawable;

    private float mTitleSize;
    private float mTextSize;

    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private NotificationCompat.Builder mBuilder;

    public void sendCustomNotification(Context context) {
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.heat);
        mBuilder.setAutoCancel(false);
        mBuilder.setOngoing(true);
        if (isDarkNotificationBar(context)) {
            mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.black_custom_notify);
        } else {
            mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.white_custom_notify);
        }
        mBuilder.setContent(mRemoteViews);
        Intent intent = new Intent(ACTION_CUSTOM_NOTIFICATION);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, 0);
//        builder.setContentIntent(pIntent);
        mRemoteViews.setTextViewTextSize(R.id.title, TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mRemoteViews.setTextColor(R.id.title, mTitleColor);
        mRemoteViews.setTextViewTextSize(R.id.text, TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mRemoteViews.setTextColor(R.id.text, mTextColor);
        mRemoteViews.setOnClickPendingIntent(R.id.button, pIntent);
        mRemoteViews.setImageViewResource(R.id.button, R.drawable.connect_wifi_press);
//        mRemoteViews.setImageViewBitmap(R.id.background, ((BitmapDrawable)mBgDrawable).getBitmap());
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

        Log.e(TAG, "");

    }
    private NotificationBroadcastReceiver mNotificationBroadcastReceiver;
    public void register(Context context) {
        if (mNotificationBroadcastReceiver == null) {
            mNotificationBroadcastReceiver = new NotificationBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter(ACTION_CUSTOM_NOTIFICATION);
            context.registerReceiver(mNotificationBroadcastReceiver, intentFilter);
        }
    }

    public void unregister(Context contex) {
        if (mNotificationBroadcastReceiver != null) {
            contex.unregisterReceiver(mNotificationBroadcastReceiver);
            mNotificationBroadcastReceiver = null;
        }
    }

    private class NotificationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive");
//            mBuilder.setContent(mRemoteViews);
            mRemoteViews.setImageViewResource(R.id.button, R.drawable.connect_cable_press);
            mNotificationManager.notify(0, mBuilder.build());
        }
    }

    public static boolean isColorSimilar(int baseColor, int color) {
        int simpleBaseColor = baseColor | 0xff000000;
        int simpleColor = color | 0xff000000;
        int baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor);
        int baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor);
        int baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor);
        double value = Math.sqrt(Math.pow(baseRed, 2) + Math.pow(baseGreen, 2) + Math.pow(baseBlue, 2));
        if (value < COLOR_THRESHOLD) {
            return true;
        }
        return false;
    }

    private boolean isDarkNotificationBar(Context context) {
        findColorAndSize(context);
        return !isColorSimilar(Color.BLACK, mTitleColor);
    }

    private void findColorAndSize(Context context) {
        if (context instanceof AppCompatActivity) {
            getColorAndSizeCompat(context);
        } else {
            getColorAndSize(context);
        }
    }

    public void getColorAndSize(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        ViewGroup notificationRoot = (ViewGroup) notification.contentView.apply(context, new FrameLayout(context));
        get(notificationRoot);
    }

    public void getColorAndSizeCompat(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.heat);
        Notification notification = builder.build();
        int layoutId = notification.contentView.getLayoutId();
        ViewGroup notificationRoot = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null);
        get(notificationRoot);
    }

    private void get(ViewGroup notificationRoot) {
        final List<TextView> textViews = new ArrayList<>();
        iteratorView(notificationRoot, new Filter() {
            @Override
            public void filter(View view) {
                if (view instanceof TextView) {
                    textViews.add((TextView) view);
                } else if (view instanceof ImageView){
                    Log.e(TAG, view.getMeasuredWidth() + "," + view.getMeasuredHeight());
                }
            }
        });
        Collections.sort(textViews, new Comparator<TextView>() {
            @Override
            public int compare(TextView o1, TextView o2) {
                float size1 = o1.getTextSize();
                float size2 = o2.getTextSize();
                if (size1 > size2) {
                    return -1;
                } else if (size1 < size2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
//        mBgDrawable = notificationRoot.getBackground();
        mTitleSize = textViews.get(0).getTextSize();
        mTitleColor = textViews.get(0).getCurrentTextColor();
        mTextSize = textViews.get(1).getTextSize();
        mTextColor = textViews.get(1).getCurrentTextColor();

    }

    private void iteratorView(View view, Filter filter) {
        if (view == null || filter == null) {
            return;
        }

        filter.filter(view);
        if (view instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) view;
            for (int i = 0, j = container.getChildCount(); i < j; i++) {
                View child = container.getChildAt(i);
                iteratorView(child, filter);
            }
        }
    }

    private interface Filter {
        void filter(View view);
    }


}
