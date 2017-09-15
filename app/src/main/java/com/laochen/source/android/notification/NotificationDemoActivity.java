package com.laochen.source.android.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.laochen.jni.R;
import com.laochen.source.ActivityB;

/**
 * Notification.Builder:Android 3.0（API 级别 11）中添加
 * 如果3.0以下可用 NotificationCompat.Builder
 * 为了确保最佳兼容性，请使用 NotificationCompat 及其子类
 */
public class NotificationDemoActivity extends AppCompatActivity {
    private Button sendBtn;
    private Button deleBtn;
    private CustomNotification mCoCustomNotification = new CustomNotification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_demo);
        sendBtn = (Button) findViewById(R.id.btn_send_notification);
        deleBtn = (Button) findViewById(R.id.btn_delete_notification);
        mCoCustomNotification.register(this);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NotificationDemoActivity.this, ActivityB.class);
//                startActivity(intent);
                mCoCustomNotification.sendCustomNotification(NotificationDemoActivity.this);
            }
        });

        deleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NotificationDemoActivity.this, ActivityC.class);
                update();
//                startActivity(intent);
            }
        });
    }

    /**
     * 创建通知的一般方法
     */
    private void trigger() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, ActivityB.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        // build notification the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.connect_wifi_press))
                .setSmallIcon(R.drawable.heat)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
//                .addAction(R.drawable.heat, "Call", pIntent)
//                .addAction(R.drawable.heat, "More", pIntent)
//                .addAction(R.drawable.heat, "And more", pIntent)
                .build();
        notificationManager.notify(0, n);
    }

    /**
     * 创建一个通知，点击后跳转到ActivityB，点返回，直接回到Home界面（ActivityB已销毁）
     */
    private void trigger2() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ActivityB.class);
        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ActivityB.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification n = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.heat)
                .setContentIntent(pIntent)
                .setAutoCancel(true) // 设为true表示用户点击通知时，通知会自动消失；否则不消失
//                .addAction(R.drawable.heat, "Call", pIntent)
//                .addAction(R.drawable.heat, "More", pIntent)
//                .addAction(R.drawable.heat, "And more", pIntent)
                .build();
        notificationManager.notify(0, n);
    }

    /**
     * 在通知中添加扩展布局，从Android 4.1开始支持
     */
    private void trigger3() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.heat)
                .setContentTitle("Event tracker")
                .setContentText("Events received");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[] {"1", "2", "3", "4", "5", "6"};
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");
        // Moves events into the expanded layout
        for (int i = 0; i < events.length; i++) {

            inboxStyle.addLine(events[i]);
        }
        // Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);
        // Issue the notification here.
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, ActivityB.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        mBuilder.setContentIntent(pIntent);
        notificationManager.notify(0, mBuilder.build());
    }

    /**
     * 更新通知：避免重复创建不必要的通知
     * 调用 NotificationManager.notify(id, Notification),id指定为需要update的旧通知的id
     * 如果之前的通知仍然可见，则系统会根据 Notification 对象的内容更新该通知。相反，如果之前的通知已被清除，系统则会创建一个新通知。
     */
    private int numMessages = 0;
    private void update() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.heat)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.");
        for (int i = 0; i < 5; i++) {
            mBuilder.setContentText("You've received new messages." + (++numMessages))
                    .setNumber(numMessages); // 通知的右下角显示消息条数
            notificationManager.notify(numMessages, mBuilder.build());
        }
    }

    /**
     * 删除通知的4种方法：
     * 1.用户手动侧滑或点击“全部清除”（如果通知可以清除）
     * 2.用户点击通知之前，设置了setAutoCancel(true)
     * 3.针对某个特定id的通知，调用cancel(id)
     * 4.调用cancelAll()，将删除之前发出的所有通知。
     */
    private void delete() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(0);
        notificationManager.cancelAll();
    }



}
