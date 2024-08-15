package fastcampus.part5.chapter2.ui


import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fastcampus.part5.chapter2.ui.theme.MyApplicationTheme
import fastcampus.part5.di.R

sealed class MainNavigationItem(var route: String, var name: String) { //3가지 네비게이션 항목 정의(각 항목은 route와 name)을 가진다.
    //sealed class 는 계층 구조를 가진 클래스를 정의할 때 사용되는 클래스이다.
    //쉽게 이야기 하면 어떤 특정 상황에서 나타날 수 있는 모든 경우의 수를 하나로 묶는 역할
    //다양한 경우를 하나로 묶어주는 클래스, 안전하고 읽기쉬운 코드 작성가능하다.
    //주로 상태나 이벤트를 표현할 때 유용하다.
    object Main : MainNavigationItem("Main", "Main")
    object Category : MainNavigationItem("Category", "Category")
    object MyPage : MainNavigationItem("MyPage", "MyPage")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() { //화면의 주요 구조 정의
    val scaffoldState = rememberScaffoldState() //scaffold 상태 저장하는 객체
    val navController = rememberNavController() //navController는 네비게이션 컨트롤러를 생성해서 화면간의 이동을 관리한다.

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            MainBottomNavigationBar(navController = navController)
        }
    ) {
        MainNavigationScreen(navController= navController)
    }
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Category,
        MainNavigationItem.MyPage
    )
    BottomNavigation(
        backgroundColor = Color(0xffffff),
        contentColor = Color(0xff00ff00)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        //navBackStackEntry는 네비게이션의 현재 상태를 나타내며, currentBackStackEntryAsState는 네비게이션 상태를 상태(State)로 관리한다.
        val currentRoute = navBackStackEntry?.destination?.route
        //현재 활성화된 화면의 경로(route)를 가져온다. 이 값을 사용해 어떤 네비게이션 아이템이 선택되었는지 확인할 수 있다.

        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                //현재 활성화된 경로가 이 item.route와 같다면, 이 아이템은 선택된 상태로 표시된다.
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            //현재 네비게이션 그래프에서 정의된 시작 화면(혹은 시작 경로)을 가져온다.
                            popUpTo(it) {
                                //네비게이션 스택에서 it에 해당하는 경로까지의 모든 화면을 제거(popup)하겠다는 뜻
                                saveState = true
                                //팝업된 화면의 상태를 저장하라
                            }
                        }
                        launchSingleTop = true
                        //동일한 화면이 여러번 스택에 쌓이지 않도록 보장한다.즉, 이미 화면이 스택에 있으면 새로운 인스턴스를 만들지 않는다.

                        restoreState = true
                        //이전에 스택에서 제거된 화면의 상태를 복원한다.
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_launcher_foreground),
                        item.route,
                    )
                },
            )
        }
    }
}

@Composable
fun MainNavigationScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainNavigationItem.Main.route){
        //NavHost는 네비게이션의 중심으로, 여러화면을 정의하고 관리하는 역할을 한다.
        //NavHost는 전달받은 navController를 사용해서 화면 간의 네비게이션을 제어한다.
        composable(MainNavigationItem.Main.route){
            Text(text = "Hello Main")
        }
        composable(MainNavigationItem.Category.route){
            Text(text = "Hello Category")
        }
        composable(MainNavigationItem.MyPage.route){
            Text(text = "Hello MyPage")
        }
    }
}

