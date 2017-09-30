package com.laochen.source.android.activity_detect.step_counter;

import android.hardware.SensorManager;

public class StepDetector2 {

    private final static String TAG = "StepDetector2";
    public static final int ALGORITHM_ID = 2;
    private float mLimit = 22.50f;
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;

    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;

    private long lastStepTimeNs = 0;
    private static final int STEP_DELAY_NS = 250000000; // 0.25秒

    private StepListener listener;

    public StepDetector2() {
        int h = 480; // TODO: remove this constant
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    }

    public void registerListener(StepListener listener) {
        this.listener = listener;
    }

    public void setSensitivity(float sensitivity) {
        mLimit = sensitivity; // 1.97  2.96  4.44  6.66  10.00  15.00  22.50  33.75  50.62
    }

    public void updateAccel(long timeNs, float x, float y, float z) {
        float vSum = 0;
        vSum += mYOffset + x * mScale[1];
        vSum += mYOffset + y * mScale[1];
        vSum += mYOffset + z * mScale[1];
        int k = 0;
        float v = vSum / 3;

        float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
        if (direction == -mLastDirections[k]) {
            // Direction changed
            int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
            mLastExtremes[extType][k] = mLastValues[k];
            float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

            if (diff > mLimit) {

                boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                boolean isNotContra = (mLastMatch != 1 - extType);

                if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra
                        && (timeNs - lastStepTimeNs > STEP_DELAY_NS)) {
                    if (listener != null) {
                        listener.step(ALGORITHM_ID, timeNs);
                    }
                    mLastMatch = extType;
                    lastStepTimeNs = timeNs;
                } else {
                    mLastMatch = -1;
                }
            }
            mLastDiff[k] = diff;
        }
        mLastDirections[k] = direction;
        mLastValues[k] = v;
    }
}