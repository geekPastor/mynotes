package dev.geekpastor.mynote.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.ui.screens.home.components.TopAppBar
import dev.geekpastor.mynote.utils.paddingAndConsumeWindowInsets
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute: NavKey

fun NavBackStack<NavKey>.navigateToHome(){
    if (lastOrNull() !is HomeRoute){
        clear()
        add(HomeRoute)
    }
}

@Composable
fun HomeScreenRoute(){
    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(scrollBehavior = scrollBehavior)
        },
        bottomBar = {}
    ) {paddingValues ->
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = {},
            modifier = Modifier
                .fillMaxSize()
                .paddingAndConsumeWindowInsets(paddingValues)
        ) {
            HomeContent()
        }
    }
}

@Composable
fun HomeContent(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Home Screen"
        )
    }
}