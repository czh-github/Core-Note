package com.laochen.source.android.activity_detect.transportation_detect;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Date:2017/9/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:能检测到的出行模式
 */

public class DetectedMode implements Parcelable {
    public static final int IN_VEHICLE = 0; // 开车
    public static final int ON_BICYCLE = 1; // 自行车
    public static final int ON_FOOT = 2; // 步行（走路+跑）
    public static final int STILL = 3; // 静止
    public static final int UNKNOWN = 4; // 检测不到
    public static final int TILTING = 5; // 设备相对于重力角度变化明显
    public static final int WALKING = 7; // 走路
    public static final int RUNNING = 8; // 跑

    /**
     * 出行模式的type
     */
    private int type;
    /**
     * 该type的可信度（0~100）
     */
    private int confidence;

    public DetectedMode(int type, int confidence) {
        this.type = type;
        this.confidence = confidence;
    }

    public int getType() {
        return type;
    }

    public int getConfidence() {
        return confidence;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        DetectedMode mode = (DetectedMode) obj;
        return this.type == mode.type && this.confidence == mode.confidence;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + type;
        result = 31 * result + confidence;
        return result;
    }

    public static String valueOf(int i) {
        switch (i) {
            case IN_VEHICLE /*0*/:
                return "IN_VEHICLE";
            case ON_BICYCLE /*1*/:
                return "ON_BICYCLE";
            case ON_FOOT /*2*/:
                return "ON_FOOT";
            case STILL /*3*/:
                return "STILL";
            case UNKNOWN /*4*/:
                return "UNKNOWN";
            case TILTING /*5*/:
                return "TILTING";
            case WALKING /*7*/:
                return "WALKING";
            case RUNNING /*8*/:
                return "RUNNING";
            default:
                return Integer.toString(i);
        }
    }

    @Override
    public String toString() {
        return "DetectedMode [type=" + valueOf(type) + ", confidence=" + confidence + "]";
    }

    public static final Parcelable.Creator<DetectedMode> CREATOR = new Creator<DetectedMode>() {
        @Override
        public DetectedMode createFromParcel(Parcel source) {
            return new DetectedMode(source);
        }

        @Override
        public DetectedMode[] newArray(int size) {
            return new DetectedMode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(confidence);
    }

    private DetectedMode(Parcel in) {
        type = in.readInt();
        confidence = in.readInt();
    }
}
