package com.sarbesh.alarmgps.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class LocationService extends Service implements LocationListener {
    private final Context mContext;

    boolean isGpsEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    protected LocationManager locationManager;

    public LocationService(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isNetworkEnabled && !isGpsEnabled) {
                //show toast/alert for enabling gps
                Log.i("Location","Location not enables");
                Toast.makeText(mContext, "Enable location....", Toast.LENGTH_SHORT).show();
                alertForLocationSetting();
            } else {
                this.canGetLocation = true;
                if (isGpsEnabled) {
                    Log.d("Location", "Getting location from GPS");
                    setLatLong(LocationManager.GPS_PROVIDER);
                }
                if (isNetworkEnabled) {
                    Log.d("Location", "Getting location from NETWORK");
                    setLatLong(LocationManager.NETWORK_PROVIDER);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return location;
    }

    /**
     * Method to check if permission provided or not
     * @return boolean
     * returns true if provided else false
     * **/
    private boolean grantedPermissionForLocationAccess() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Method requesting access for location
     * **/
    private void requestLocationPermission() {
        Log.d("permission","Requesting permission");
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 101);
    }

    /**
     * set latitude and longitude variables
     * */
    private void setLatLong(String provider) {
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("permission","Permission not granted");
            requestLocationPermission();
        }
        locationManager.requestLocationUpdates(provider, 0, 0, this);
        if(null != locationManager){
            Log.d("Location","Manager not null");
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                Log.i("Location","Got location: "+location);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
    }

    /**
     * Method to get latitude
     * */

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Method to get longitude
     * */

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Method to check GPS/wifi enabled
     * @return boolean
     * */

    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    /**
     * Method to show GPS settings if not enabled
     */
    public void alertForLocationSetting(){
        Log.i("Settings","Showing alert for enabling GPS");
        AlertDialog.Builder settingsAlertDialog = new AlertDialog.Builder(mContext);

        //Setting dialog title and message
        settingsAlertDialog.setTitle("Enable GPS in Setting");
        settingsAlertDialog.setMessage("GPS not enabled. Do you want to enable in setting");

        //On pressing button
        settingsAlertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("Settings","Accepted");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("Settings","Not Accepted");
                dialogInterface.cancel();
            }
        });
        settingsAlertDialog.show();
    }

    /**
     * Stop using GPS listener
     * Calling this Method will stop using GPS in your app
     * */

    public void stopUsingGPS(){
        if(locationManager != null){
            Log.i("Location","Stopping");
            locationManager.removeUpdates(LocationService.this);
        }
    }

    @Override
    public void onFlushComplete(int requestCode) {
        stopUsingGPS();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        stopUsingGPS();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
