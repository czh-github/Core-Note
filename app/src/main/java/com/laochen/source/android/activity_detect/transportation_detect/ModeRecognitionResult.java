package com.laochen.source.android.activity_detect.transportation_detect;

import android.content.Intent;

import java.util.Collections;
import java.util.List;

/**
 * Date:2017/9/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:封装识别出来的交通模式结果
 */

public class ModeRecognitionResult {
    private long time;
    private long elapsedRealtimeMillis;
    private List<DetectedMode> probableModes;

    /**
     * 构造方法
     *
     * @param probableModes         被检测到的modes，按可信度高低排序（可信度高的排前面）
     * @param time                  本次检测发生的时间（UTC）
     * @param elapsedRealtimeMillis 自上次手机启动到现在的毫秒数
     */
    public ModeRecognitionResult(List<DetectedMode> probableModes, long time, long elapsedRealtimeMillis) {
        this.probableModes = probableModes;
        this.time = time;
        this.elapsedRealtimeMillis = elapsedRealtimeMillis;
    }

    /**
     * 构造方法
     *
     * @param mostProbableMode      被检测到的可能性最大的mode
     * @param time                  本次检测发生的时间（UTC）
     * @param elapsedRealtimeMillis 自上次手机启动到现在的毫秒数
     */
    public ModeRecognitionResult(DetectedMode mostProbableMode, long time, long elapsedRealtimeMillis) {
        this(Collections.singletonList(mostProbableMode), time, elapsedRealtimeMillis);
    }

    /**
     * 从intent中提取检测结果
     * 如果intent里不包含ModeRecognitionResult返回null
     */
    public static ModeRecognitionResult extractResult(Intent intent) {
        return null;
    }

    public int getModeConfidence(int type) {
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return elapsedRealtimeMillis;
    }

    public long getTime() {
        return time;
    }

    public DetectedMode getMostProbableMode() {
        if (probableModes != null && probableModes.size() > 0) {
            return probableModes.get(0);
        }
        return null;
    }

    public List<DetectedMode> getProbableModes() {
        return probableModes;
    }


}
