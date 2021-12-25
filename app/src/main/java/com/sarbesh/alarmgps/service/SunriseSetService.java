package com.sarbesh.alarmgps.service;

import com.sarbesh.alarmgps.dto.RiseSetApiResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ApiInterface
 */
public interface SunriseSetService {

    @GET("/json?")
    Call<RiseSetApiResponse> getSunRiseSet(@Query("lat") double lat, @Query("lng") double lng,
                                           @Query("date") String date);

    @GET("/json?")
    Call<RiseSetApiResponse> getSunRiseSet(@Query("lat") double lat, @Query("lng") double lng);
}
