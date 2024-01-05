package de.androidnewcomer.schrittzaehler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;


public class ErschuetterungListener implements SensorEventListener {

    private static final float SCHWELLWERT = 500f;
    private Handler handler;
    private boolean schrittBegonnen;
    private float schwellwert = SCHWELLWERT;

    public ErschuetterungListener(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float betrag = event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2];
        if(schrittBegonnen)
        {
            if(betrag>schwellwert)
            {
                schrittBegonnen = false;
                handler.sendEmptyMessage(1);
            }
        }
        else
        {
            if(betrag< schwellwert)
            {
                schrittBegonnen= true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void setSchwellwert(float schwellwert) {
        this.schwellwert = schwellwert;
    }
}
