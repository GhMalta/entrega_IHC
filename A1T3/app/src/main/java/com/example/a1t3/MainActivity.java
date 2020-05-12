package com.example.a1t3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView x_value, y_value, z_value;
    private SensorManager sensorManager;
    private Sensor sensor;

    float x, y, z, x_old, y_old, z_old;
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x_value = findViewById(R.id.x);
        y_value = findViewById(R.id.y);
        z_value = findViewById(R.id.z);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        firstTime = true;
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x_old = x;
            y_old = y;
            z_old = z;

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            x_value.setText(String.valueOf(x));
            y_value.setText(String.valueOf(y));
            z_value.setText(String.valueOf(z));

            boolean sign_diff_x = Math.abs(x - x_old) > 4;
            boolean sign_diff_y = Math.abs(x - x_old) > 4;
            boolean sign_diff_z = Math.abs(x - x_old) > 4;

            if (!firstTime && (sign_diff_x || sign_diff_y || sign_diff_z)) {
                moveToNextActivity(x, y, z);
            }

            firstTime = false;
        }
    }

    public void moveToNextActivity(float x, float y, float z) {
        Intent intent = new Intent(this, NextActivity.class);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        intent.putExtra("z", z);
        startActivity(intent);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

