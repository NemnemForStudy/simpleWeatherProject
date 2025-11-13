package com.example.weatherapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.ui.search.SearchScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 이 테스트 클래스를 AndroidJUnit4 테스트 실행기로 실행하도록 지정합니다.
 * AndroidJUnit4는 안드로이드 환경에서 JUnit 테스트를 실행할 수 있게 해주는 클래스입니다.
 * UI 테스트는 실제 안드로이드 기기나 에뮬레이터에서 실행되어야 하므로 이 설정이 필요합니다.
 */
@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    /**
     * @get:Rule
     * JUnit의 규칙(Rule)을 정의하는 어노테이션입니다.
     * createComposeRule()은 Compose UI 테스트를 위한 환경을 설정하고, 테스트를 제어할 수 있는 ComposeTestRule 객체를 생성합니다.
     * 이 객체를 사용하여 Composable을 렌더링하고, UI 요소를 찾고, 상호작용하고, 상태를 검증할 수 있습니다.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * @Test
     * 이 메소드가 테스트 케이스임을 나타냅니다.
     * JUnit은 이 어노테이션이 붙은 메소드를 테스트로 인식하고 실행합니다.
     */
    @Test
    fun searchScreen_initialState() {
        // setContent를 사용하여 테스트할 Composable UI를 렌더링합니다.
        // 테스트 환경에서 SearchScreen을 화면에 표시하는 역할을 합니다.
        composeTestRule.setContent {
            // 테스트하려는 UI가 앱의 다른 부분과 일관된 테마를 갖도록
            // 실제 앱에서 사용하는 WeatherAppTheme으로 감싸줍니다.
            WeatherAppTheme {
                SearchScreen()
            }
        }

        // onNodeWithText("City Name")는 화면에서 "City Name"이라는 텍스트를 가진 UI 노드(요소)를 찾습니다.
        // assertIsDisplayed()는 해당 노드가 화면에 실제로 표시되고 있는지 확인하는 검증 메소드입니다.
        // 즉, "City Name" 이라는 라벨을 가진 입력창이 보이는지 테스트합니다.
        composeTestRule.onNodeWithText("City Name").assertIsDisplayed()

        // 위와 동일하게, "Search"라는 텍스트를 가진 버튼이 화면에 표시되는지 확인합니다.
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
    }

    @Test
    fun searchScreen_textInput() {
        composeTestRule.setContent {
            WeatherAppTheme {
                SearchScreen()
            }
        }

        // "City Name" 라벨을 가진 입력창 노드를 찾아서 "Seoul" 텍스트를 입력합니다.
        composeTestRule.onNodeWithText("City Name").performTextInput("Seoul")

        // 입력 후, "Seoul"이라는 텍스트를 가진 노드가 화면에 표시되는지 확인합니다.
        composeTestRule.onNodeWithText("Seoul").assertIsDisplayed()
    }
}
