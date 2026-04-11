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
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.google.firebase.auth.FirebaseAuth
import dev.geekpastor.mynote.R
import dev.geekpastor.mynote.ui.screens.create.CreateNoteRoute
import dev.geekpastor.mynote.ui.screens.create.navigateToCreateNote
import dev.geekpastor.mynote.ui.screens.details.NoteDetailRoute
import dev.geekpastor.mynote.ui.screens.details.NoteDetailScreen
import dev.geekpastor.mynote.ui.screens.details.navigateToNoteDetail
import dev.geekpastor.mynote.ui.screens.home.HomeRoute
import dev.geekpastor.mynote.ui.screens.home.HomeScreenRoute
import dev.geekpastor.mynote.ui.screens.home.navigateToHome
import dev.geekpastor.mynote.ui.screens.login.LoginRoute
import dev.geekpastor.mynote.ui.screens.login.LoginScreenRoute

@Composable
fun MyNoteNavHost(
    modifier: Modifier = Modifier,
    webClientToken: String = ""
) {
    val user = FirebaseAuth.getInstance().currentUser

    val backStack = if (user != null){
        rememberNavBackStack(HomeRoute)
    } else{
        rememberNavBackStack(LoginRoute)
    }

    val currentRoute = backStack.lastOrNull()

    Scaffold(
        modifier = modifier,

        bottomBar = {
            if (currentRoute !is LoginRoute) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onHomeClick = backStack::navigateToHome
                )
            }
        }
    ) { innerPadding ->

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),

            entryProvider = entryProvider {



                entry<LoginRoute> {
                    LoginScreenRoute(
                        navigateHome = {
                            backStack.navigateToHome()
                        },
                        webClientToken = webClientToken
                    )
                }
                
                
                entry<HomeRoute> {
                    HomeScreenRoute(
                        onNoteClick = { note ->
                            backStack.navigateToNoteDetail(note.id)
                        },
                        navigateToCreateNote = {
                            backStack.navigateToCreateNote()
                        }
                    )
                }

                entry<CreateNoteRoute>{
                    CreateNoteRoute(
                        onBack = {
                            backStack.removeLastOrNull()
                        }
                    )
                }

                entry<NoteDetailRoute> { route ->
                    NoteDetailScreen(
                        noteId = route.noteId,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }
            }
        )
    }
}

@Composable
private fun BottomNavigationBar(
    currentRoute: NavKey?,
    onHomeClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute is HomeRoute,
            onClick = onHomeClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = "Home"
                )
            },
            label = { Text("Mes notes") }
        )

        NavigationBarItem(
            selected = currentRoute is HomeRoute,
            onClick = onHomeClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_favorite_border),
                    contentDescription = "Favorite notes icon"
                )
            },
            label = { Text("Notes Favorites") }
        )
    }
}