package com.laochen.source.android;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Question:stopSelf() vs stopSelf(int) vs stopService(Intent)
 *
 * A started service must manage its own lifecycle.
 * That is, the system does not stop or destroy the service unless
 * it must recover system memory and the service continues to run after onStartCommand() returns.
 * So, the service must stop itself by calling stopSelf() or another component can stop it by calling stopService().

 Once requested to stop with stopSelf() or stopService(), the system destroys the service as soon as possible.

 However, if your service handles multiple requests to onStartCommand() concurrently(并发),
 then you shouldn't stop the service when you're done processing a start request,
 because you might have since received(很快收到) a new start request (stopping at the end of
 the first request would terminate the second one). To avoid this problem, you can use stopSelf(int)
 to ensure that your request to stop the service is always based on the most recent start request.

 That is, when you call stopSelf(int), you pass the ID of the start request
 (the startId delivered to onStartCommand()) to which your stop request corresponds.
 Then if the service received a new start request before you were able to call stopSelf(int),
 then the ID will not match and the service will not stop.
 */

public class MyService extends Service {
    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
