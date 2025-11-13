package com.example.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.SearchViewModel

// ViewModel 생성 위해 Repo 생성사로 받음
class SearchViewModelFactory (
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {
    // ViewModel 인스턴스 생성하는 역할 하는 메소드
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            // 호환된다면, Repository 주입해 SearchViewModel 인스턴스 생성하고 반환.
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(weatherRepository) as T
        }
        // 만약 알 수 없는 ViewModel 클래스를 요청하면 예외 발생
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}