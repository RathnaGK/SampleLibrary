package com.example.mylibrary.service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.mylibrary.model.LocationData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.Realm;
import io.realm.RealmResults;

public class LatLngDatabase extends Service
{

    Realm realm;
    ConnectivityManager connectivityManager;
    RealmResults<LocationData> DisplayData;
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();
         connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Subscribe(sticky = true)
    public void onEvent(PositionService.LatLng event)
    {
        realm.beginTransaction();
        LocationData mLocationData = realm.createObject(LocationData.class);
        mLocationData.setLatitiude(event.getLatitude());
        mLocationData.setLongitude(event.getLongitude());
        realm.commitTransaction();


        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected())
        {
            DisplayData = realm.where(LocationData.class).findAll();
            Log.d("PositionService", "onLocationChanged: "+DisplayData);
            Toast.makeText(LatLngDatabase.this, ""+DisplayData, Toast.LENGTH_SHORT).show();
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        realm.close();
    }
}
