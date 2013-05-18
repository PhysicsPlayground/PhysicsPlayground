package com.PhysicsPlayground.physicsplayground;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    private double PrevAcceleration;
    private long PrevTime = System.currentTimeMillis();
    private long ActualTime;
    private long PrevActualTime;
    private long CurrentVelocity;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
      /*requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      view = findViewById(R.id.IntroText);
      view.setBackgroundColor(Color.GREEN);

      sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
      lastUpdate = System.currentTimeMillis();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public void onSensorChanged(SensorEvent event) {
      if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        getAccelerometer(event);
      }

    }
    
    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        TextView AccelerationX = (TextView) findViewById(R.id.AccelerationX);
        TextView AccelerationY = (TextView) findViewById(R.id.AccelerationY);
        TextView AccelerationZ = (TextView) findViewById(R.id.AccelerationZ);
        TextView ta = (TextView) findViewById(R.id.textViewa);
        TextView tt = (TextView) findViewById(R.id.textViewt);
        TextView tv = (TextView) findViewById(R.id.textViewv);
        AccelerationX.setText("My x value is MICHAEL WUZ HERE:" + x);
        AccelerationY.setText("My y value is Mouhand was not here:" + y);
        AccelerationZ.setText("My z value is :" + z);
        //float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        double RealAcceleration = Math.sqrt(x * x + y * y + z * z) - PrevAcceleration;
        PrevAcceleration = Math.sqrt(x * x + y * y + z * z);
        ActualTime = (System.currentTimeMillis() - PrevTime) + ActualTime;
        PrevTime = System.currentTimeMillis();
        ta.setText("My Real Acceleration value is :" + RealAcceleration);
        tt.setText("My t value is :" + (ActualTime/1000));
        CurrentVelocity = (long) (CurrentVelocity+(RealAcceleration *(ActualTime - PrevActualTime)/1000.0));
        tv.setText("My v value is :\n" + CurrentVelocity);
        /*if (accelationSquareRoot >= 2) //
        {
          if (actualTime - lastUpdate < 200) {
            return;
          }
          lastUpdate = actualTime;
          Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
              .show();
          if (color) {
            view.setBackgroundColor(Color.GREEN);

          } else {
            view.setBackgroundColor(Color.RED);
          }
          color = !color;
        }*/
      }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    
    @Override
    protected void onResume() {
      super.onResume();
      // register this class as a listener for the orientation and
      // accelerometer sensors
      sensorManager.registerListener(this,
          sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onPause() {
      // unregister listener
      super.onPause();
      sensorManager.unregisterListener(this);
    }
    
}
