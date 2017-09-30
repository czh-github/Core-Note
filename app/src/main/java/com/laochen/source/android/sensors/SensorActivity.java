package com.laochen.source.android.sensors;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.laochen.jni.R;

import java.util.Arrays;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Accelerometer";
    private SensorManager sensorManager;
    double ax, ay, az;   // these are the acceleration in x,y and z axis

    // 加速度传感器
    private Sensor mAccelerometerSensor;

    // 磁力传感器
    private Sensor mMagneticSensor;

    // 方向传感器
    private Sensor mOrientationSensor;

    // 陀螺仪传感器
    private Sensor mGyroscopeSensor;

    // 光线感应传感器
    private Sensor mLightSensor;

    // 压力传感器
    private Sensor mPressureSensor;

    // 温度传感器
    private Sensor mTemperatureSensor;

    // 重力传感器
    private Sensor mGravitySensor;

    // 线性加速度传感器
    private Sensor mLinearAccelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        sensorManager.registerListener(this, mMagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mOrientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//        sensorManager.registerListener(this, mOrientationSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mGyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        sensorManager.registerListener(this, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mLightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        sensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mPressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
//        sensorManager.registerListener(this, mPressureSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
//        sensorManager.registerListener(this, mTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mGravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
//        sensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);

//        mLinearAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
//        sensorManager.registerListener(this, mLinearAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        boolean sensorStepDetector = getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
        boolean sensorStepCounter = getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);
        Log.e(TAG, "sensorStepDetector:" + sensorStepDetector);
        Log.e(TAG, "sensorStepCounter:" + sensorStepCounter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
            Log.e(TAG, "x=" + ax + ", y=" + ay + ", z=" + az);
        } else if (type == Sensor.TYPE_MAGNETIC_FIELD) {
            Log.e(TAG, "磁力计(微特斯拉uT)：x=" + event.values[0] + ", y=" + event.values[1] + ", z=" + event.values[2]);
        } else if (type == Sensor.TYPE_ORIENTATION) {
            Log.e(TAG,"方向传感器:" + Arrays.toString(event.values));
        } else if (type == Sensor.TYPE_GYROSCOPE) {
            Log.e(TAG,"陀螺仪:" + Arrays.toString(event.values));
        } else if (type == Sensor.TYPE_LIGHT) {
            Log.e(TAG,"光线:" + Arrays.toString(event.values));
        } else if (type == Sensor.TYPE_PRESSURE) {
            Log.e(TAG,"压强:" + Arrays.toString(event.values));
        } else if (type == Sensor.TYPE_TEMPERATURE) {
            Log.e(TAG,"温度:" + Arrays.toString(event.values));
        } else if (type == Sensor.TYPE_GRAVITY) {
            Log.e(TAG,"重力加速度:" + Arrays.toString(event.values));
        } else if (type == Sensor.TYPE_LINEAR_ACCELERATION) {
            Log.e(TAG,"线性加速度（减去重力加速度）:" + Arrays.toString(event.values));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
