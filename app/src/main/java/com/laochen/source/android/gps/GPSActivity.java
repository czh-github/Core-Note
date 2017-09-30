package com.laochen.source.android.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.laochen.jni.R;

public class GPSActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        init();
    }

    private void init() {
        //获取位置服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);  //设置获取经纬度的精度为最高，等价criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);

        criteria.setAltitudeRequired(true);  //获取海拔信息
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH); // 设置获取海拔的精度为最高

        criteria.setBearingRequired(true);  //获取方位信息
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH); // 设置获取方位信息的精度为最高

        criteria.setSpeedRequired(true); // 获取速度信息
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH); // 设置获取速度信息的精度为最高

        criteria.setCostAllowed(false);  //不允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW);  // 使用省电模式

        // 获得当前的位置提供者
        String provider = /*locationManager.getBestProvider(criteria, true)*/LocationManager.NETWORK_PROVIDER;
//        // 获得当前的位置
//        Location location = locationManager.getLastKnownLocation(provider);
//        Geocoder gc = new Geocoder(this);
//        List<Address> addresses = null;
//        try {
//            //根据经纬度获得地址信息
//            addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (addresses.size() > 0) {
//            //获取address类的成员信息
//            String msg = "";
//            msg += "AddressLine：" + addresses.get(0).getAddressLine(0) + "\n";
//            msg += "CountryName：" + addresses.get(0).getCountryName() + "\n";
//            msg += "Locality：" + addresses.get(0).getLocality() + "\n";
//            msg += "FeatureName：" + addresses.get(0).getFeatureName();
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                float accuracy = location.getAccuracy();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
                float speed = location.getSpeed();
                float bearing = location.getBearing();
                Log.e("Location0", "精度：" + accuracy + "\n经度：" + longitude + "\n纬度：" + latitude + "\n海拔：" + altitude + "\n速度：" + speed + "\n方位：" + bearing);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(provider, 5000, 0, locationListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }
}
