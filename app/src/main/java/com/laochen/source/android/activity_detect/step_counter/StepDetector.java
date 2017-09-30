package com.laochen.source.android.activity_detect.step_counter;

public class StepDetector {
    public static final int ALGORITHM_ID =1;

    private static final int ACCEL_RING_SIZE = 50; // 加速度采样数
  private static final int VEL_RING_SIZE = 10; // 速度采样数
  
  // change this threshold according to your sensitivity preferences
    // 该值越小计步越敏感
  private static final float STEP_THRESHOLD = 20f;
 
  private static final int STEP_DELAY_NS = 250000000; // 0.25秒
 
  private int accelRingCounter = 0;
  private float[] accelRingX = new float[ACCEL_RING_SIZE];
  private float[] accelRingY = new float[ACCEL_RING_SIZE];
  private float[] accelRingZ = new float[ACCEL_RING_SIZE];
  private int velRingCounter = 0;
  private float[] velRing = new float[VEL_RING_SIZE];
  private long lastStepTimeNs = 0;
  private float oldVelocityEstimate = 0;
 
  private StepListener listener;
 
  public void registerListener(StepListener listener) {
    this.listener = listener;
  }
 
  
  public void updateAccel(long timeNs, float x, float y, float z) {
    float[] currentAccel = new float[3];
    currentAccel[0] = x;
    currentAccel[1] = y;
    currentAccel[2] = z;
 
    // First step is to update our guess of where the global z vector is.
    accelRingCounter++;
    accelRingX[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[0];
    accelRingY[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[1];
    accelRingZ[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[2];
 
    float[] worldZ = new float[3];
      // 最多50个x轴加速度值的平均值
    worldZ[0] = SensorFilter.sum(accelRingX) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
      // 最多50个y轴加速度值的平均值
    worldZ[1] = SensorFilter.sum(accelRingY) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
      // 最多50个z轴加速度值的平均值
    worldZ[2] = SensorFilter.sum(accelRingZ) / Math.min(accelRingCounter, ACCEL_RING_SIZE);

      // 对worldZ求模
    float normalization_factor = SensorFilter.norm(worldZ);

      // 各分量的单位分量的值
    worldZ[0] = worldZ[0] / normalization_factor;
    worldZ[1] = worldZ[1] / normalization_factor;
    worldZ[2] = worldZ[2] / normalization_factor;

      // Next step is to figure out the component of the current acceleration
      // in the direction of world_z and subtract gravity's contribution
    float currentZ = SensorFilter.dot(worldZ, currentAccel) - normalization_factor;

    velRingCounter++;
    velRing[velRingCounter % VEL_RING_SIZE] = currentZ;
 
    float velocityEstimate = SensorFilter.sum(velRing);

      // 判断满足什么条件算走了一步
    if (velocityEstimate > STEP_THRESHOLD
            && oldVelocityEstimate <= STEP_THRESHOLD
                && (timeNs - lastStepTimeNs > STEP_DELAY_NS)) {
          listener.step(ALGORITHM_ID, timeNs);
          lastStepTimeNs = timeNs;
    }
    oldVelocityEstimate = velocityEstimate;
  }
}