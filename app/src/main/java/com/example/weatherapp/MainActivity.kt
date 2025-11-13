package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.repository.WeatherRepositoryImpl
import com.example.weatherapp.service.RetrofitClient
import com.example.weatherapp.service.WeatherSearvice
import com.example.weatherapp.ui.search.SearchScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewModel.SearchViewModelFactory
import com.example.weatherapp.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 네트워크 서비스 생성 : Retrofit을 사용해 Service 인터페이스 구현체를 만듦.
        val weatherService = RetrofitClient.instance.create(WeatherSearvice::class.java)

        // 2. Repo 생성: 네트워크 서비스 필요로 하는 WeatherRepositoryImple 만듦
        val weatherRepository = WeatherRepositoryImpl(weatherService)

        // 3. ViewModel 팩토리 생성: Repo 필요하는 SearchViewModelFactory 만듦
        val viewModelFactory = SearchViewModelFactory(weatherRepository)

        // 4. ViewModel 생성: 팩토리 사용해 SearchViewModel 인스턴스 가져옴
        //    ViewModelProvider가 ViewModel의 생명주기 관리해줌
        val viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
//                WeatherApp()
                // 앱의 메인 화면으로 SearchScreen 표시

                // 5. 완성된 ViewModel을 SearchScreen에 전달
                SearchScreen(viewModel = viewModel)
            }
        }
    }
}