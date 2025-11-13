package com.example.weatherapp.service

import com.example.weatherapp.model.WeatherResponse
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
