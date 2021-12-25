package com.sarbesh.alarmgps.utils;

import com.sarbesh.alarmgps.service.SunriseSetService;

public class ApiClient {

    private static final String SUNRISESET_URL = "https://api.sunrise-sunset.org/";

    public SunriseSetService getSunriseSet(){
        return RetrofitInstance.getRetrofit(SUNRISESET_URL).create(SunriseSetService.class);
    }
}
