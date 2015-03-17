package com.app.toby.findmycharger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.app.toby.findmycharger.MainActivity.*;

/**
 * Created by Toby on 12/11/2014.
 */

public class PowerStateManager extends BroadcastReceiver {
    private Boolean charging = false;
    //private LocManager mLoManager;
    private Location mlocation;


    private LocationManager locationMangaer=null;
    private LocationListener locationListener=null;

    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb =null;
    private Context mContext;
    // private static final String TAG = "Debug";
    private Boolean flag = true;




    private static final String TAG = "PowerStateManager";

    @Override
    public void onReceive(Context context, Intent intent) {





        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;


        if (isCharging == true) {
            Log.d(TAG, " charging");
            charging = false;


        }
        if (isCharging == false) {
            if (charging == false) {
                charging = true;
                Log.d(TAG, " not Charging");




                    GPSTracker gps =  new GPSTracker(context.getApplicationContext());

                    // check if GPS enabled
                    if(gps.canGetLocation()){

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        String lat = String.valueOf(latitude);
                        String lon = String.valueOf(longitude);



                        Date date = new Date();
                        String sDate = date.toString();
                        Data data = null;
                        data = DataSource.createData(sDate, lon, lat);

                        // toast for testing
                       Toast.makeText(context, "Your Location is - Lat: " + lat + " Long: " + lon + data, Toast.LENGTH_LONG).show();
                    } else {
                        String dd = "0.00000";
                        Date date = new Date();
                        String sDate = date.toString();
                        Data data = null;
                        data = DataSource.createData(sDate, dd, dd);

                    }
                gps.stopUsingGPS();






            }
        }






    }




}
