package com.laochen.source.java.collection.queue;

import android.app.Application;

/**
 * Date:2017/7/13 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class JniApplication extends Application {
    private static JniApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static JniApplication get() {
        return app;
    }
}
