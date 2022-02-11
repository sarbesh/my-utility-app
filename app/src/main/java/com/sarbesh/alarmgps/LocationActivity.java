package com.sarbesh.alarmgps;

import static com.sarbesh.alarmgps.utils.Constants.LATITUDE;
import static com.sarbesh.alarmgps.utils.Constants.LONGITUDE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sarbesh.alarmgps.service.LocationService;

import java.text.MessageFormat;

public class LocationActivity extends AppCompatActivity {

    private TextView showLocation;
    private Button getData;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        showLocation = findViewById(R.id.showLocation);
        getData = findViewById(R.id.load_data);
        getData.setEnabled(false);
    }

    public void getLocation(View view){
        Log.d("Location","Get Location");
        boolean gotGpsData = getGpsData(latitude, longitude);
        if(gotGpsData){
            Log.d("Data","Enabling getData button "+latitude+", "+longitude);
            getData.setEnabled(true);
        }
    }

    public void getSolarData(View view){
        Intent intent = new Intent(getApplicationContext(),SunRaiseSetActivity.class);
        Log.d("Data","setting: "+latitude+", "+longitude);
        intent.putExtra(LATITUDE,latitude);
        intent.putExtra(LONGITUDE,longitude);
        startActivity(intent);
    }

    private boolean getGpsData(Double latitude, Double longitude){
        LocationService locationService = new LocationService(this);
        if(locationService.canGetLocation()){
            this.latitude = locationService.getLatitude();
            this.longitude = locationService.getLongitude();
            Log.d("Location",this.latitude+" "+this.longitude);
            showLocation.setText(MessageFormat.format(
                    "Your Location: \n Latitude: {0}\n Longitude: {1}",
                    String.valueOf(this.latitude), String.valueOf(this.longitude)));
            return true;
        } else {
            locationService.alertForLocationSetting();
            return false;
        }
    }
}