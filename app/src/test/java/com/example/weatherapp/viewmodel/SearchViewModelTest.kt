package com.example.weatherapp.viewmodel

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * ViewModel의 유닛 테스트에서는 실제 Repository를 사용하지 않고,
 * 테스트용으로 만든 가짜(Fake) Repository를 사용합니다.
 * 이렇게 하면 네트워크 통신이나 데이터베이스 작업 없이 ViewModel의 로직만 순수하게 테스트할 수 있습니다.
 */
class FakeWeatherRepository : WeatherRepository {
    // getWeather 함수가 어떤 도시 이름으로 호출되었는지 저장하는 변수
    var calledWithCity: String? = null

    override suspend fun getWeather(cityName: String): WeatherResponse? {
        // 함수가 호출되면, 어떤 도시 이름으로 호출되었는지 기록합니다.
        calledWithCity = cityName
        // 테스트에서는 실제 날씨 데이터가 필요 없으므로 null을 반환합니다.
        return null
    }
}

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    // 코루틴 테스트를 위한 Test Dispatcher 입니다.
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel
    private lateinit var fakeRepository: FakeWeatherRepository

    /**
     * @Before: 각각의 테스트 메소드가 실행되기 전에 항상 먼저 실행되는 부분입니다.
     * 테스트 환경을 초기화하는 역할을 합니다.
     */
    @Before
    fun setup() {
        // 테스트 환경에서는 실제 안드로이드의 Main 스레드를 사용할 수 없으므로, testDispatcher로 대체합니다.
        Dispatchers.setMain(testDispatcher)
        // 테스트용 가짜 Repository를 생성합니다.
        fakeRepository = FakeWeatherRepository()
        // ViewModel을 생성할 때, 가짜 Repository를 주입해줍니다.
        // !! 중요: 이 코드는 아직 SearchViewModel이 Repository를 받을 준비가 되지 않았기 때문에 컴파일 오류가 발생합니다.
        viewModel = SearchViewModel(fakeRepository)
    }

    /**
     * @After: 각 테스트 메소드가 끝난 후에 실행됩니다.
     * 테스트 환경을 정리하는 역할을 합니다.
     */
    @After
    fun tearDown() {
        // 테스트가 끝난 후, main dispatcher를 원래대로 되돌려 놓습니다.
        Dispatchers.resetMain()
    }

    /**
     * 테스트 내용: searchWeather 함수가 올바른 도시 이름으로 Repository의 함수를 호출하는지 검증
     */
    @Test
    // 함수 이름에 백틱 사용하는 이유 - 백틱으로 감싸면 명명규칙(띄어쓰기, 특수문자) 사용 가능.
    // runTest - 코루틴 코드는 일반적인 테스트 환경에서 제대로 동작하지 않는다. 작업이 끝나기 기다리지 않고 테스트 함수가 먼저 종료되는데 이 문제를 해결해줌.
    fun `searchWeather calls repository with correct city name`() = runTest {
        // Given (주어진 상황)
        val cityName = "Seoul"

        // When (무엇을 했을 때)
        viewModel.searchWeather(cityName)

        // Then (결과는 이래야 한다)
        // fakeRepository의 getWeather 함수가 "Seoul"이라는 도시 이름으로 호출되었는지 확인합니다.
        assertEquals(cityName, fakeRepository.calledWithCity)
    }
}
