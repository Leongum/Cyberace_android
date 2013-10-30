package com.G5432.Cyberace.Run;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Message;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import com.G5432.Cyberace.R;
import com.G5432.StepCounting.SCCommon;
import com.G5432.Utils.CommonUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static java.lang.String.*;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-10-30
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class RunningBasic extends Activity implements SensorEventListener{
    private SensorManager mySensorManager;
    private Sensor mySensor;
    private SensorEvent mySensorEvent;
    private Timer timer;

    // 定义Handler
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView textView = (TextView)findViewById(R.id.gAccReading);
            textView.setText(msg.obj.toString());
        }
    };

    private void initSensors(){
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_normal);
        initSensors();
    }

    protected void onResume() {
        super.onResume();
        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_FASTEST);
        timer=new Timer();
                /*定义计划任务，根据参数的不同可以完成以下种类的工作：
                 * 在固定时间执行某任务，在固定时间开始重复执行某任务，
                 * 重复时间间隔可控，在延迟多久后执行某任务，在延迟多久
                 * 后重复执行某任务，重复时间间隔可控
                 */
        timer.schedule(new TimerTask() {
            // TimerTask 是个抽象类,实现的是Runable类
            @Override
            public void run() {
                // TODO Auto-generated method stub
//                      Message message=new Message();
//                      message.what=i--;
                Message msg = new Message();
                msg.obj =  valueOf(SCCommon.calculateVectorMod(mySensorEvent.values));
                handler.sendMessage(msg);

            }
        }, 1000,50);
    }

    protected void onStop() {
        mySensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
        mySensorEvent = sensorEvent;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
