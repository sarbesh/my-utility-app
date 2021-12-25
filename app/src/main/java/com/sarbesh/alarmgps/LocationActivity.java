package com.sarbesh.alarmgps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sarbesh.alarmgps.service.LocationService;

import java.text.MessageFormat;

public class LocationActivity extends AppCompatActivity {

    private TextView showLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button getLocation = findViewById(R.id.getLocation);
        showLocation = findViewById(R.id.showLocation);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Location","Get Location");
                getGpsData();
            }
        });
    }

    private void getGpsData(){
        LocationService locationService = new LocationService(this);
        if(locationService.canGetLocation()){
            String latitude = String.valueOf(locationService.getLatitude());
            String longitude = String.valueOf(locationService.getLongitude());
            showLocation.setText(MessageFormat.format("Your Location: \n Latitude: {0}\n Longitude: {1}", latitude, longitude));
        } else {
            locationService.alertForLocationSetting();
        }
    }
}