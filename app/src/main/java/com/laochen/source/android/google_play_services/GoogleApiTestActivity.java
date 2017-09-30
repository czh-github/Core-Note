package com.laochen.source.android.google_play_services;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.laochen.jni.R;

import java.util.List;

public class GoogleApiTestActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final String TAG = "GoogleApiTestActivity";
    private TextView tvInfo;
    /**
     * 提供访问Google Play services的入口.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * 发起/停止定位
     */
    private FusedLocationProviderClient mFusedLocationProviderClient;

    /**
     * 封装定位请求相关参数
     */
    private LocationRequest mLocationRequest;

    /**
     * 封装定位结果
     */
    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_api_test);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkGooglePlayServicesAvailable() {
        // 除了通过回调检查Google Play services是否可用，
        // 还可以通过下面这种方式判断
        int googlePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (googlePlayServicesAvailable == ConnectionResult.SUCCESS) {
            Log.e(TAG, "googlePlayServicesAvailable:SUCCESS");
        } else {
            Log.e(TAG, "googlePlayServicesAvailable:" + googlePlayServicesAvailable);
        }
    }

    public void onClickBuildGoogleApiClient(View view) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this) // 自动管理连接（onStart()后自动connect()，onStop()后自动disconnect()）
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void onClickConnectGoogleApiClient(View view) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void onClickDisconnectGoogleApiClient(View view) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onClickGetLastKnownLocation(View view) {
        if (mFusedLocationProviderClient != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // location为null的原因：
                    // 1.设备的定位功能关闭了；
                    // 2.设备从未记录过位置（新机或者恢复了出厂设置）；
                    // 3.Google play services重启，导致FusedLocationProviderClient不能生效。
                    Log.e(TAG, "Last known location:" + (location == null ? null :location.toString()));

                    // TODO location有速度属性，记录10个速度值取平均值，低于5m/s认为是步行
                }
            });
        }
    }

    private LocationRequest createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000);
            mLocationRequest.setFastestInterval(2000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        return mLocationRequest;
    }

    /**
     * 检查当前手机的定位设置是否满足我们期望的LocationRequest
     */
    private void checkLocationRequestSatisfied() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize location requests here.
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                if (statusCode == CommonStatusCodes.RESOLUTION_REQUIRED) {
                    // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(GoogleApiTestActivity.this, 1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                } else if (statusCode == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                    // Location settings are not satisfied. However, we have no way to fix the settings so we won't show the dialog.
                }
            }
        });
    }

    public void onClickStartLocationUpdates(View view) {
        if (mGoogleApiClient.isConnected()) {
            checkLocationRequestSatisfied();
        }
    }

    public void onClickStopLocationUpdates(View view) {
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            return;
        }
        if (mLocationCallback == null) {
            mLocationCallback =  new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    List<Location> locations = locationResult.getLocations();
                    Log.e(TAG, "Result locations:" + locations);
                    Location lastLocation = locationResult.getLastLocation();
                    Log.e(TAG, "Last location:" + lastLocation);
                    tvInfo.setText(locations.toString());
                }
            };
            Task<Void> task = mFusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), mLocationCallback, null);
            Log.e(TAG,"Task is successful:" + task.isSuccessful());
        }
    }

    private void stopLocationUpdates() {
        if (mLocationCallback != null) {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            mLocationCallback = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // All location settings are satisfied. The client can initialize location requests here.
                startLocationUpdates();
            } else {
                // 用户没有授权满足我们期望的LocationRequest，意味着无法进行高精度定位
                Log.e(TAG, "用户没有授权满足我们期望的LocationRequest，意味着无法进行高精度定位");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onClickGetLastKnownLocation(null);
            }
        } else if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently
        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "Connected to GoogleApiClient Successfully");
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.e(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
}
