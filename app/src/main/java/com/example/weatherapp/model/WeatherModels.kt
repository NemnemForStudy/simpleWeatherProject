package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

/**
 * API 응답을 파싱해 담을 데이터 클래스(DTO) 들
 * Gson 라이브러리가 JSON 응답을 아래 객체들로 자동 변환
 * @SerializedName 어노테이션은 JSON 키 이름과 Kotlin 프로퍼티 이름 매핑함.
 */

/**
 * OpenWeatherApp API 응답의 최상위 구조에 해당하는 데이터 클래스.
 */
data class WeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("name") val name: String
)

/**
 * 날씨 정보("맑음", 아이콘 ID) 담는 데이터 클래스
 */
data class Weather(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)


/**
 * 온도, 체감 온도 등 주요 날씨 데이터를 담는 데이터 클래스입니다.
 */
data class Main(
    @SerializedName("temp") val temp: Double, // 현재 온도
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double
)
