package de.androidnewcomer.schrittzaehler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;



public class SchrittzaehlerService extends Service
{
    public static Handler ereignisHandler;
    private SensorManager sensorManager;
    private Sensor sensor;
    private ErschuetterungsHandler handler = new ErschuetterungsHandler();
    private ErschuetterungListener listener = new ErschuetterungListener(handler);
    public static int schritte=0;

    private class ErschuetterungsHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            schritte++;
            if(ereignisHandler!=null)
                ereignisHandler.sendEmptyMessage(schritte);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onDestroy(){
        sensorManager.unregisterListener(listener);
        super.onDestroy();
    }
}
