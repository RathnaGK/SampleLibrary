package com.example.mylibrary.service;


import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.mylibrary.model.LocalLog;

import org.greenrobot.eventbus.EventBus;

public class PositionService extends Service
{
    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    Location location; // Location

    // LocalLogService mLocalLogService;
    protected LocationManager locationManager;
    private LocationListener mLocationListener = new PositionListner();
    private LocalLog mLocalLog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocalLog = new LocalLog();

        //realm = Realm.getDefaultInstance();
        Intent mIntent = new Intent(this, LatLngDatabase.class);
        startService(mIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            Toast.makeText(this, "no network provider is enable", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            }
            if (isNetworkEnabled)
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
            }
        }

        return START_STICKY;
    }

    public class PositionListner implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location) {
            double latitude =  location.getLatitude();
            double longitude = location.getLongitude();

            Log.d("PositionService", "onLocationChanged: "+latitude+"...."+longitude);

            mLocalLog.log_d("latitude "+latitude+"........."+"longitude "+longitude);
            LatLng event = new LatLng();
            event.setLatitude(latitude);
            event.setLongitude(longitude);

            EventBus.getDefault().postSticky(event);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    public class LatLng
    {
        private double latitude;
        private double longitude;

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

}

