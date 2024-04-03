    package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


    private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

    private val formattedDateList = getNextSevenDaysFormattedDates()


    interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getPropertiesAll(@Query("start_date") startDate: String = formattedDateList[0], @Query("end_date") endDate: String = formattedDateList[7], @Query("api_key") apiKey: String = "DEMO_KEY"): String
        @GET("neo/rest/v1/feed")
        suspend fun getPropertiesWeek(@Query("start_date") startDate: String = formattedDateList[1], @Query("end_date") endDate: String = formattedDateList[7], @Query("api_key") apiKey: String = "DEMO_KEY"): String
        @GET("neo/rest/v1/feed")
        suspend fun getPropertiesToday(@Query("start_date") startDate: String = formattedDateList[0], @Query("end_date") endDate: String = formattedDateList[0], @Query("api_key") apiKey: String = "DEMO_KEY"): String
    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String="the key") : PictureOfDay
}

object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}

