package com.sarbesh.alarmgps;

import static com.sarbesh.alarmgps.utils.AndroidUtils.showToast;
import static com.sarbesh.alarmgps.utils.AndroidUtils.utcToLocal;
import static com.sarbesh.alarmgps.utils.Constants.LATITUDE;
import static com.sarbesh.alarmgps.utils.Constants.LONGITUDE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sarbesh.alarmgps.dto.RiseSetApiResponse;
import com.sarbesh.alarmgps.dto.RiseSetResponse;
import com.sarbesh.alarmgps.utils.ApiClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunRaiseSetActivity extends AppCompatActivity {

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
    private TextView tomorrowDate;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_raise_set);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra(LATITUDE,0);
        double lng = intent.getDoubleExtra(LONGITUDE,0);

        // Show progress overlay (with animation):
//        AndroidUtils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);

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
        tomorrowDate = findViewById(R.id.DateView);

        loadData(lat,lng);

        // Hide it (with animation):
//        AndroidUtils.animateView(progressOverlay, View.GONE, 0, 200);

    }

    private void loadData(double lat, double lng){
        progressBar.setProgress(10);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tomorrowAsString = dateFormat.format(tomorrow);
        tomorrowDate.setText(tomorrowAsString);

        ApiClient apiClient = new ApiClient();
        Call<RiseSetApiResponse> sunRiseSet = apiClient.getSunriseSet().getSunRiseSet(lat,lng,tomorrowAsString);
        progressBar.setProgress(20);
        sunRiseSet.enqueue(new Callback<RiseSetApiResponse>() {
            @Override
            public void onResponse(Call<RiseSetApiResponse> call, Response<RiseSetApiResponse> response) {
                progressBar.setProgress(75);
                if(response.isSuccessful()){
                    RiseSetApiResponse body = response.body();
                    if(null!=body) {
                        RiseSetResponse results = body.getResults();
                        Log.i("response", results.toString());

                        SimpleDateFormat utcFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                        utcFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                        SimpleDateFormat localFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                        localFormat.setTimeZone(TimeZone.getDefault());
                        progressBar.setProgress(85);
                        try{
                            sunrise.setText(utcToLocal(utcFormat,localFormat,results.getSunrise()));
                            sunset.setText(utcToLocal(utcFormat,localFormat,results.getSunset()));
                            dayLength.setText(results.getDayLength());
                            solarNoon.setText(utcToLocal(utcFormat,localFormat,results.getSolarNoon()));
                            civilTwilightBegin.setText(utcToLocal(utcFormat,localFormat,results.getCivilTwilightBegin()));
                            civilTwilightEnd.setText(utcToLocal(utcFormat,localFormat,results.getCivilTwilightEnd()));
                            nauticalTwilightBegin.setText(utcToLocal(utcFormat,localFormat,results.getNauticalTwilightBegin()));
                            nauticalTwilightEnd.setText(utcToLocal(utcFormat,localFormat,results.getNauticalTwilightEnd()));
                            astronomicalTwilightBegin.setText(utcToLocal(utcFormat,localFormat,results.getAstronomicalTwilightBegin()));
                            astronomicalTwilightEnd.setText(utcToLocal(utcFormat,localFormat,results.getAstronomicalTwilightEnd()));
                        } catch (ParseException ex){
                            showToast(" Date ParseException");
                        }
                        progressBar.setProgress(100);
                        Log.d("ProgressBar","Cancelling progressbar");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RiseSetApiResponse> call, Throwable t) {
                showToast("Call failed");
                call.cancel();
                progressBar.setProgress(100);
                Log.d("ProgressBar","Cancelling progressbar");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}