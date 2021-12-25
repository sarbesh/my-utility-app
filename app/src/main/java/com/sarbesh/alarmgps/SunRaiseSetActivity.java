package com.sarbesh.alarmgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sarbesh.alarmgps.dto.RiseSetApiResponse;
import com.sarbesh.alarmgps.dto.RiseSetResponse;
import com.sarbesh.alarmgps.service.SunriseSetService;
import com.sarbesh.alarmgps.utils.ApiClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunRaiseSetActivity extends AppCompatActivity {

    private SunriseSetService sunriseSetService;

    private TextView sunset;
    private TextView sunrise;
    private TextView dayLength;
    private TextView solarNoon;
    private TextView civilTwilightBegin;
    private TextView civilTwilightEnd;
    private TextView nauticalTwilightBegin;
    private TextView nauticalTwilightEnd;
    private TextView astronomicalTwilightBegin;
    private TextView astronomicalTwilightEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_raise_set);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("Latitude",0);
        double lng = intent.getDoubleExtra("Longitude",0);

        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        dayLength = findViewById(R.id.day_length);
        solarNoon = findViewById(R.id.solar_noon);
        civilTwilightBegin = findViewById(R.id.civil_twilight_begin);
        civilTwilightEnd = findViewById(R.id.civil_twilight_end);
        nauticalTwilightBegin = findViewById(R.id.nautical_twilight_begin);
        nauticalTwilightEnd = findViewById(R.id.nautical_twilight_end);
        astronomicalTwilightBegin = findViewById(R.id.astronomical_twilight_begin);
        astronomicalTwilightEnd = findViewById(R.id.astronomical_twilight_end);

        loadData(lat,lng);

    }

    private void loadData(double lat, double lng){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrowAsString = dateFormat.format(tomorrow);

        ApiClient apiClient = new ApiClient();
        Call<RiseSetApiResponse> sunRiseSet = apiClient.getSunriseSet().getSunRiseSet(lat,lng,tomorrowAsString);
        sunRiseSet.enqueue(new Callback<RiseSetApiResponse>() {
            @Override
            public void onResponse(Call<RiseSetApiResponse> call, Response<RiseSetApiResponse> response) {
                if(response.isSuccessful()){
                    RiseSetApiResponse body = response.body();
                    if(null!=body) {
                        RiseSetResponse results = body.getResults();
                        Log.i("response", results.toString());
                        //TODO: Fix layout design and map data to filed
                        sunrise.setText(results.getSunrise());
                    }
                }
            }

            @Override
            public void onFailure(Call<RiseSetApiResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}