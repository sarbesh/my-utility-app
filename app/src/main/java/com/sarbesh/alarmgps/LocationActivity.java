package com.sarbesh.alarmgps;

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
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button getLocation = findViewById(R.id.getLocation);
        showLocation = findViewById(R.id.showLocation);

        Button getData = findViewById(R.id.load_data);
        getData.setEnabled(false);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Location","Get Location");
                boolean gotGpsData = getGpsData(latitude, longitude);
                if(gotGpsData){
                    Log.d("Data","Enabling getData button "+latitude+", "+longitude);
                    getData.setEnabled(true);
                }
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SunRaiseSetActivity.class);
                Log.d("Data","setting: "+latitude+", "+longitude);
                intent.putExtra("Latitude",latitude);
                intent.putExtra("Longitude",longitude);
                startActivity(intent);
            }
        });
    }

    private boolean getGpsData(Double latitude, Double longitude){
        LocationService locationService = new LocationService(this);
        if(locationService.canGetLocation()){
            this.latitude = locationService.getLatitude();
            this.longitude = locationService.getLongitude();
            Log.d("Location",latitude+" "+longitude);
            showLocation.setText(MessageFormat.format("Your Location: \n Latitude: {0}\n Longitude: {1}", latitude, longitude));
            return true;
        } else {
            locationService.alertForLocationSetting();
            return false;
        }
    }
}