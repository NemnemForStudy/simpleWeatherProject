package com.example.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

/**
 * 날씨 정보를 보여주는 UI 상태 나타내는 클래스
 * sealed interface를 사용해 가능한 모든 상태를 명확히 정의함.
 */
sealed interface WeatherUIState {
    /* 초기 상태 : 아무것도 검색 X */
    object Initial : WeatherUIState
    /* 로딩 상태 : 데이터 가져오는 샅애 */
    object Loading : WeatherUIState
    /* 성공 상태 : 데이터를 성공적으로 가져온 상태 */
    data class Success(val weatherData: WeatherResponse) : WeatherUIState
    /* 에러 상태 : 데이터 가져오는 데 실패한 상태 */
    // 에러 상태가 어떤 종류의 에러인지 정보를 담고 있어야하기 때문임.
    data class Error(val message: String) : WeatherUIState
}

// ViewModel: View(UI)에 필요한 데이터를 제공하고 로직을 처리합니다.
class SearchViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    // 1. View가 관찰할 상태 (검색어)
    var searchText by mutableStateOf("")
        private set

    // 2. View로부터 호출될 상태 변경 로직
    // 초기 상태는 Initial로 설정
    var weatherUIState: WeatherUIState by mutableStateOf(WeatherUIState.Initial)
        private set

    // 검색어 업데이트
    fun updateSearchText(newText: String) {
        searchText = newText
    }

    // 3. 비즈니스 로직 (날씨 검색)
    // 날씨 검색하는 비즈니스 로직
    fun searchWeather(city: String) {
        if (city.isNotBlank()) {
            viewModelScope.launch {
                // 검색 시작 시 UI 상태를 로딩으로 변경
                weatherUIState = WeatherUIState.Loading

                // Repo 통해 날씨 데이터 요청
                // 네트워크 오류 날 수 있으니 try-catch
                try {
                    val response = weatherRepository.getWeather(city)

                    // 3. 응답 결과에 따라 UI 상태 변경
                    weatherUIState = if(response != null) {
                        WeatherUIState.Success(response)
                    } else {
                        WeatherUIState.Error("도시를 찾을 수 없거나, 네트워크에 문제가 있습니다.")
                    }
                } catch (e: Exception) {
                    // 예외 발생 시 : 'Error' 상태로 변경하고 오류 메시지 전달
                    weatherUIState = WeatherUIState.Error("오류가 발생했습니다: ${e.message}")
                }
            }
        }
    }
}
