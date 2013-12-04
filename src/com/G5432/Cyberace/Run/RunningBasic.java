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
import com.G5432.StepCounting.*;
import com.G5432.Utils.CommonUtil;

import java.text.MessageFormat;
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
public class RunningBasic extends Activity {
    private AccSensorService accSensorService;
    private OrientationSensorService orientationSensorService;
    private Timer timer;

    // 定义Handler
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            //TextView textView = (TextView) findViewById(R.id.gAccReading);
            //textView.setText(msg.obj.toString());
        }
    };


    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.running_normal);
        accSensorService = new AccSensorService(this);
        orientationSensorService = new OrientationSensorService(this);
        accSensorService.start();
        orientationSensorService.start();
    }

    protected void onResume() {
        super.onResume();

        timer = new Timer();
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

                solveDeviceStatus();
            }
        }, 1000, 50);
    }

    protected void onStop() {
        super.onStop();
    }

    private void solveDeviceStatus() {
        float R[] = new float[9];
        SensorManager.getRotationMatrix(R, null, accSensorService.getOriginAcc().v, orientationSensorService.getOriginGeomagnetic().v);
        DeviceStatus newStatus = new DeviceStatus(accSensorService.getOriginAcc(), R);
        Message msg = new Message();
        msg.obj = MessageFormat.format("{0}\n{1}\n{2}", newStatus.getRealAcc().v[0], newStatus.getRealAcc().v[1], newStatus.getRealAcc().v[2]);
        handler.sendMessage(msg);
    }
}
