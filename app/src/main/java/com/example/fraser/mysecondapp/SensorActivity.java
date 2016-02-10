package com.example.fraser.mysecondapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.widget.TextView;


public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText,yText,zText, detectShake, x1Text,y1Text,z1Text, movementText;
    private Sensor mySensor;
    private SensorManager SM;

    public Vibrator v;

    //detect shake gesture
    private long lastUpdate = 0;
    private float last_x, last_y, last_z, last_x1, last_y1, last_z1;
    private static final int SHAKE_THRESHOLD = 600;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
        //acceleremoter
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //register sensor to listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        detectShake = (TextView)findViewById(R.id.shakeDetect);
        x1Text = (TextView)findViewById(R.id.x1Text);
        y1Text = (TextView)findViewById(R.id.y1Text);
        z1Text = (TextView)findViewById(R.id.z1Text);
        movementText = (TextView)findViewById(R.id.movement);

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

    }
    /*
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


       /* xText.setText("X: "+ sensorEvent.values[0]);
        yText.setText("Y: "+ sensorEvent.values[1]);
        zText.setText("Z: "+ sensorEvent.values[2]);

        /////////////////////////////
        //if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER)
        //{
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100)
            {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD)
                {
                    detectShake.setText("Shake: Device Shaked!!!!");
                    v.vibrate(75);
                }
                //else
                  //  detectShake.setText("Shake: No Shake");

                last_x = x;
                last_y = y;
                last_z = z;

                xText.setText("X: "+ last_x);
                yText.setText("Y: "+ last_y);
                zText.setText("Z: "+ last_z);
            }*/
        //}
        ///////////////////////////////////////

       /*final float alpha = (float) 0.8;
       // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        // acceleration
        float x1 = sensorEvent.values[0] - gravity[0];
        float y1 = sensorEvent.values[1] - gravity[1];
        float z1 = sensorEvent.values[2] - gravity[2];

        if(x1 > 1)
        {
            movementText.setText("Movement: Up");
        }
        else
            movementText.setText("Movement: ");


        last_x = x1;
        last_y = y1;
        last_z = z1;
        // acceleration including gravity
        //sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2];
        x1Text.setText("X: "+ last_x);
        y1Text.setText("Y: "+ last_y);
        z1Text.setText("Z: "+ last_z);

    }*/
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        final float alpha = (float) 0.8;
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        // acceleration
        float x = sensorEvent.values[0] - linear_acceleration[0];
        float y = sensorEvent.values[1] - linear_acceleration[1];
        float z = sensorEvent.values[2] - linear_acceleration[2];

        //float x = event.values[0];
        //float y = event.values[1];
        //float z = event.values[2];

        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);

        if (x > y)
        {
            if (x < 0) {
                x1Text.setText("Right");//switch these values??
            }
            if (x > 0) {
                x1Text.setText("Left");//with this one
            }

        }
        else
        {
            if (y < 0)
            {
                x1Text.setText("Up");
            }
            if (y > 0)
            {
                x1Text.setText("Down");
            }
        }

        if (x > (-2) && x < (2) && y > (-2) && y < (2)) {

            x1Text.setText("Middle");

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    //not needed here
    }

    protected void onPause() {
        super.onPause();
        SM.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //open proximity sensor activity
    public void openProximity(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ProximityActivity.class);
        startActivity(intent);
    }
}
