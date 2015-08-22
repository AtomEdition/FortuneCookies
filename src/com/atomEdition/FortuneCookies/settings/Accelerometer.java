package com.atomEdition.FortuneCookies.settings;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.utils.ActivityUtils;
import com.atomEdition.FortuneCookies.utils.CookieUtils;

/**
 * Created by FruityDevil on 18.12.14.
 */
public class Accelerometer extends ContextWrapper {

    private static SensorManager sensorManager;
    private static Sensor sensor;

    private float lastX, lastY, lastZ;

    public Accelerometer(Context baseContext){
        super(baseContext);
    }

    public void setSensors(){
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public boolean isSensorRegistered(){
        return sensor != null;
    }

    public void registerSensorListener(SensorEventListener sensorEventListener){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unRegisterSensorListener(SensorEventListener sensorEventListener){
        sensorManager.unregisterListener(sensorEventListener);
    }

    public boolean isShakeEnough(float x, float y, float z){
        double force = 0.0d;
        force += Math.pow((x - lastX) / SensorManager.GRAVITY_EARTH, 2.0);
        force += Math.pow((y - lastY) / SensorManager.GRAVITY_EARTH, 2.0);
        force += Math.pow((z - lastZ) / SensorManager.GRAVITY_EARTH, 2.0);
        force = Math.sqrt(force);

        lastX = x;
        lastY = y;
        lastZ = z;

        if(force > Utils.SHAKE_THRESHOLD){
            Utils.SHAKE_COUNT_CURRENT++;
            if(Utils.SHAKE_COUNT_CURRENT > Utils.SHAKE_COUNT){
                Utils.SHAKE_COUNT_CURRENT = 0;
                lastX = 0;
                lastY = 0;
                lastZ = 0;
                return true;
            }
        }
        return false;
    }

    public void calibrate(float x, float y, float z){
        double force = 0.0d;
        force += Math.pow((x - lastX) / SensorManager.GRAVITY_EARTH, 2.0);
        force += Math.pow((y - lastY) / SensorManager.GRAVITY_EARTH, 2.0);
        force += Math.pow((z - lastZ) / SensorManager.GRAVITY_EARTH, 2.0);
        force = Math.sqrt(force);

        lastX = x;
        lastY = y;
        lastZ = z;

        if((force > Utils.SHAKE_THRESHOLD_DEFAULT) && (Utils.CALIBRATION.size() < 10))
            Utils.CALIBRATION.add(force);
        else if(Utils.CALIBRATION.size() >= 10){
            Toast.makeText(this, R.string.calibrate_success, Toast.LENGTH_LONG).show();
            setCalibration(Utils.average(Utils.CALIBRATION));
            ActivityUtils.vibrator.vibrate(Utils.VIBRATE_FALL_TIME);
            Utils.CALIBRATION.clear();
            new CookieUtils(this, ActivityUtils.activity).prepareCookies();
        }
    }

    public void setCalibration(Double calibration){
        Utils.SHAKE_THRESHOLD = calibration;
        SharedPreferences sharedPreferences = this.getSharedPreferences(Utils.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(Utils.PREFERENCES_SHAKE_THRESHOLD, calibration.floatValue());
        editor.commit();
    }

    public void loadCalibration(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(Utils.PREFERENCES_NAME, 0);
        Float calibration = sharedPreferences.getFloat(Utils.PREFERENCES_SHAKE_THRESHOLD, 0F);
        Utils.SHAKE_THRESHOLD = calibration.doubleValue();
    }
}
