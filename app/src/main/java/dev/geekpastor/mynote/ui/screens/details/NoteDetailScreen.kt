package dev.geekpastor.mynote.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.ui.screens.home.components.TopAppBar
import kotlinx.serialization.Serializable

@Serializable
data class NoteDetailRoute(
    val noteId: String
) : NavKey

fun NavBackStack<NavKey>.navigateToNoteDetail(noteId: String) {
    add(NoteDetailRoute(noteId))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    BackHandler {
        onBack()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = "Titre de la note",
                onBackClick = onBack
            )
        },
        bottomBar = {}
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Note ID: $noteId")

            // plus tard : récupérer la note depuis ViewModel
        }

    }
}