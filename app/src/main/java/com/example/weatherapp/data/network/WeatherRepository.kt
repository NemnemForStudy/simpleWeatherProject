package com.example.weatherapp.data.network

/**
 * 날씨 데이터 소스를 추상화하는 리포지토리 인터페이스
 * ViewModel은 이 인터페이스에 의존하며, 실제 구현에 대해 알 필요가 없다.
 * 이렇게 하면 ViewModel과 데이터 소스 간 결합도가 낮아져 테스트하기 좋은 구조가 됨.
 */

interface WeatherRepository {
    /**
     * 특정 도시 날씨 데이터 가져옴
     * @param cityName 날씨를 조회할 도시 이름
     * @return 성공 시 WeatherResponse 객체를, 실패시 Null 반환
     */

    /**
     * suspend 사용 이유
     * 1. 함수가 오래 걸릴 수 있으니 특별하게 다뤄주세요.
     * 2. 스레드를 차단하지 않고 일시 중단
     * 3. 작업 완료 후 안전하게 복귀
     */
    suspend fun getWeather(cityName: String): WeatherResponse?
}

/**
 * WeatherRepository의 실제 구현체
 * 네트워크 API인 WeatherService를 통해 데이터 가져옴
 * @param weatherService Retrofit으로 생성된 날씨 API서비스, 생성자 통해 외부에서 주입받음(의존성 주입)
 */
class WeatherRepositoryImpl(
    private val weatherSearvice: WeatherSearvice
) : WeatherRepository {
    // TODO: 실제 OpenWeatherMap에서 발급받은 API 키 넣어야 함.
    private val apiKey = ""

    override suspend fun getWeather(cityName: String): WeatherResponse? {
        // 네트워크 요청 중 발생할 수 있는 예외 처리위해 try- catch
        return try {
            // WeatherService를 호출해 API로부터 날씨 데이터를 받아온다.
            weatherSearvice.getWeatherData(
                cityName = cityName,
                apiKey = apiKey,
            )
        } catch (e: Exception) {
            // 네트워크 오류, 서버 문제 등 예외 발생 시 null 반환
            // 실제 앱에서는 로그를 남기거나 오류 타입에 따라 다른 처리 못함.
            e.printStackTrace()
            null
        }
    }
}