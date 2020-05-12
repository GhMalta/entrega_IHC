package com.example.a2t1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private Sensor proximity;

    TextView lightValue;
    TextView proximityValue;

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_LIGHT)
        {
            lightValue.setText("Light Intensity: " + event.values[0]);
        }
        if(sensor.getType() == Sensor.TYPE_PROXIMITY)
        {
            proximityValue.setText("Proximity (cm): " + event.values[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightValue = (TextView)findViewById(R.id.light);
        proximityValue = (TextView)findViewById(R.id.proximity_id);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(light != null)
        {
            sensorManager.registerListener(MainActivity.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
            lightValue.setText("Light sensor not supported");
        }

        if(proximity != null)
        {
            sensorManager.registerListener(MainActivity.this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }else
        {
            proximityValue.setText("Proximity sensor not supported");
        }
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, light, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, sensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}

