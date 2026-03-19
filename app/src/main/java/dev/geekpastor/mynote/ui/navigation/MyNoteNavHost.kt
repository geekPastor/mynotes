package dev.geekpastor.mynote.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.geekpastor.mynote.R
import dev.geekpastor.mynote.ui.screens.home.HomeRoute
import dev.geekpastor.mynote.ui.screens.home.HomeScreenRoute
import dev.geekpastor.mynote.ui.screens.home.navigateToHome
import dev.geekpastor.mynote.ui.screens.login.LoginRoute
import dev.geekpastor.mynote.ui.screens.login.LoginScreen

@Composable
fun MyNoteNavHost(
    modifier: Modifier = Modifier
){
    val backStack  = rememberNavBackStack(LoginRoute)

    Scaffold(
        modifier = modifier,
        bottomBar = {

            if (backStack.lastOrNull() !is LoginRoute){

                NavigationBar {
                    NavigationBarItem(
                        selected = backStack.lastOrNull() is HomeRoute,
                        onClick = backStack::navigateToHome,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_home),
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Mes notes") }
                    )

                    /*NavigationBarItem(
                        selected = backStack.lastOrNull() is SearchRoute,
                        onClick = backStack::navigateToSearch,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_search),
                                contentDescription = "Search"
                            )
                        },
                        label = { Text("Search") }
                    )*/

                }

            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = {backStack.removeLastOrNull()},
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            entryProvider = entryProvider {
                entry<HomeRoute>{
                    HomeScreenRoute()
                }

                entry<LoginRoute>{
                    LoginScreen(
                        onLoginSuccess = {
                            backStack.navigateToHome()
                        }
                    )
                }
            }
        )
    }
}