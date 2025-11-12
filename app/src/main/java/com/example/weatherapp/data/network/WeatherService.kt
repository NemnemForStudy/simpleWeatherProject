package com.example.weatherapp.data.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit을 사용해 날씨 API와 통신하기 위한 인터페이스.
 * 실제 네트워크 요청을 정의하는 메소드들이 포함됨.
 */

interface WeatherSearvice {
    /**
     * suspend 키워드는 이 함수가 코루틴 내 호출되어야 하는 비동기 함수임을 나타냄.
     * 네트워크 요청은  시간이 걸리는 작업, UI 스레드를 차단하지 않고 백그라운드에서 실행
     * @GET 어노테이션은 HTTP GET 요청
     * 괄호 안 API의 기본 URL뒤에 붙는 상세 경로 지정.
     *
     * @Query 어노테이션은 메소드 파라미터를 요청 URL의 쿼리 파라미터로 추가
     */

    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") cityName: String, // 도시 이름
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric", // 온도를 섭씨로 받기위해 추가
        @Query("lang") lang: String = "kr" // 결과 한글
    ): WeatherResponse // API 응답을 담을 데이터 클래스
}

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