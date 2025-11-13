package com.example.weatherapp.ui.search


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.viewmodel.SearchViewModel
import com.example.weatherapp.viewmodel.WeatherUIState

/** @Composable 어노테이션은 함수가 Jetpack Compose UI의 일부임을 나타냄.
 * 화면에 표시될 UI 컴포넌트 정의하는 함수임.
 */
@Composable
fun SearchScreen(
    /**
     * viewModel() 함수는 LifeCycle 라이브러리에서 제공하는 기능으로,
     * 이 Composable이 활성화되어 있는 동안 SearchViewModel의 인스턴스 생성, 관리
     * UI 생명주기가 변경되어도 ViewModel은 유지되므로 데이터 손실 안됨.
     * SearchScreen은 viewModel을 통해 UI상태(검색어)를 얻고, 상태 변경 요청
     */
    viewModel: SearchViewModel
) {
    // UI 상태를 ViewModel로 부터 가져옴
    val uiState = viewModel.weatherUIState

    // Column은 자식 컴포넌트를 수직으로 차례대로 배치하는 레이아웃.
    Column (
        /**
         * modifier는 Composable의 모양, 동작을 꾸미는 데 사용됨.
         * 여기서는 Column 너비를 화면 전체로 채우고 상하좌우에 16dp 패딩을 줌.
         */
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        // 1. 검색어 입력창
        // OutlinedTextField 사용자가 텍스트를 입력할 수 있는 UI 컴포넌트
        // 테두리가 있는 디자인을 가짐.
        OutlinedTextField(
            // value: 텍스트 필드에 표시될 값. viewModel searchText 상태와 연결되어 있음.
            value = viewModel.searchText,
            onValueChange = { viewModel.updateSearchText(it) },
            // label: 텍스트 필드가 비어있을 때 보여주는 안내 텍스트(힌트)입니다.
            label = { Text("City Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 2. 검색 버튼
        Button(
            onClick = { viewModel.searchWeather(viewModel.searchText) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 여기서부터 UI 상태에 따라 다른 화면을 보여줌
        when(val state = uiState) {
            is WeatherUIState.Initial -> {
                Text(
                    text = "날씨를 검색할 도시를 입력해주세요.",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is WeatherUIState.Loading -> {
                // 로딩 상태일 때: 로딩 인디케이터 보여주기
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is WeatherUIState.Success -> {
                // 성공 상태일 때 : 날씨 정보 담은 카드 보여주기
                WeatherInfoCard(weatherData = state.weatherData)
            }
            is WeatherUIState.Error -> {
                // 에러일때 빨간 텍스트로 보여줌
                Text(
                    text = state.message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

/**
 * 날씨 정보를 예쁘게 표시하는 카드 Composable
 */
@Composable
fun WeatherInfoCard(weatherData: WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 도시 이름
            Text(
                text = weatherData.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 날씨 설명
            weatherData.weather.firstOrNull()?.let { weather ->
                Text(
                    text = weather.description,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // 현재 온도
            Text(
                text = "${weatherData.main.temp}°C",
                fontSize = 48.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}