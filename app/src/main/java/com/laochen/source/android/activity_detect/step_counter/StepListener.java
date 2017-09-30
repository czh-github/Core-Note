package com.laochen.source.android.activity_detect.step_counter;

public interface StepListener {

    /**
     * @param id 使用何种算法
     * Called when a step has been detected.  Given the time in nanoseconds at
     * which the step was detected.
     */
    void step(int id, long timeNs);

}