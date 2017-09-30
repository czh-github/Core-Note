package com.laochen.source.android.activity_detect.step_counter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laochen.jni.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    private TextView textView, tvDetect, tvBuildInCounts;
    private Button start, stop;
    private StepDetector simpleStepDetector;
    private StepDetector2 simpleStepDetector2;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private int numSteps2;

    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mStepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        simpleStepDetector2 = new StepDetector2();
        simpleStepDetector2.registerListener(this);

        textView = (TextView) findViewById(R.id.tv_steps);
        tvDetect = (TextView) findViewById(R.id.tv_detect);
        tvBuildInCounts = (TextView) findViewById(R.id.tv_build_in_steps);
        start = (Button) findViewById(R.id.btn_start);
        stop = (Button) findViewById(R.id.btn_stop);



        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                numSteps2 = 0;
                sensorManager.registerListener(StepCounterActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                sensorManager.registerListener(StepCounterActivity.this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
                sensorManager.registerListener(StepCounterActivity.this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sensorManager.unregisterListener(StepCounterActivity.this, accel);
                sensorManager.unregisterListener(StepCounterActivity.this, mStepCounterSensor);
                sensorManager.unregisterListener(StepCounterActivity.this, mStepDetectorSensor);

            }
        });



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
            simpleStepDetector2.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            tvBuildInCounts.setText("内置传感器检测总步数 : " + event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
//            tvDetect.setText("内置传感器检测是否正在走路 : " + formatTime(event.timestamp) + "," + event.values[0]);
        }
    }

    @Override
    public void step(int id, long timeNs) {
        if (id == StepDetector.ALGORITHM_ID) {
            numSteps++;
            textView.setText("算法1：" + numSteps);
        } else if (id == StepDetector2.ALGORITHM_ID) {
            numSteps2++;
            tvDetect.setText("算法2：" + numSteps2);
        }
    }

    public String formatTime(long nanosecond) {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        }
        return simpleDateFormat.format(new Date(nanosecond / 1000));
    }

    private SimpleDateFormat simpleDateFormat;
}
