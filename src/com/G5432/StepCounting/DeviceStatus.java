package com.G5432.StepCounting;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-10-30
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public class DeviceStatus {
    public SensorEvent getgAccVector() {
        return gAccVector;
    }

    public void setgAccVector(SensorEvent gAccVector) {
        this.gAccVector = gAccVector;
    }

    private SensorEvent gAccVector;


}
