package com.example.weatherapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit 인스턴스를 생성, 관리하는 싱글턴(object) 객체이다.
 * 앱 전체에서 단 하나의 Retrofit 인스턴스만 사용하도록 보장해 리소스를 효율적으로 관리함.
 */

object RetrofitClient {
    // OpenWeatherMap API 기본 URL
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    /**
     * Retrofit 인스턴스를 lazy 초기화 방식으로 생성함.
     * 'lazy'는 이 코드가 처음으로 사용될 때 단 한번만 실행되도록 보장함.
     */

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // API 기본 URL 설정
            .addConverterFactory(GsonConverterFactory.create()) // JSON 데이터를 Kotlin 객체로 변환해주는 Gson 컨버터
            .build() // Retrofit 인스턴스 생성.
    }
}