package com.example.weatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// ViewModel: View(UI)에 필요한 데이터를 제공하고 로직을 처리합니다.
class SearchViewModel : ViewModel() {
    // 1. View가 관찰할 상태 (검색어)
    // Compose의 상태 변수를 ViewModel 내에 정의합니다.
    var searchText by mutableStateOf("")
        private set // 외부(View) 에서는 값 변경 못하도록 설정

    // 2. View로부터 호출될 상태 변경 로직
    fun updateSearchText(newText: String) {
        searchText = newText
    }

    // 3. 비즈니스 로직(TODO: 날씨 API 호출 및 Room DB 저장)
    fun searchWeather(city: String) {
        if(city.isNotBlank()) {
            println("VIEWMODEL: 날씨 검색 요청 - 도시: $city")
            // TODO: Retrofit을 사용한 API 호출 로직 구현 예정
        }
    }
}