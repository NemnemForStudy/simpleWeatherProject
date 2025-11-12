package com.example.weatherapp.ui.search


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.SearchViewModel

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
    viewModel: SearchViewModel = viewModel()
) {
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
            label = { Text("도시 이름") },
            modifier = Modifier.fillMaxWidth()
        )


        // 2. 검색 버튼
        Button(
            onClick = { viewModel.searchWeather(viewModel.searchText) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("검색")
        }
    }
}