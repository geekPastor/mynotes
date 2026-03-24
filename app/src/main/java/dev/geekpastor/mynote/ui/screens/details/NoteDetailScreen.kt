package dev.geekpastor.mynote.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.ui.screens.create.component.CreateNoteTopBar
import dev.geekpastor.mynote.ui.screens.home.components.MyTopAppBar
import kotlinx.serialization.Serializable

@Serializable
data class NoteDetailRoute(
    val noteId: String
) : NavKey

fun NavBackStack<NavKey>.navigateToNoteDetail(noteId: String) {
    add(NoteDetailRoute(noteId))
}

@OptIn(ExperimentalMaterial3Api::class)
/**
 * Displays note details.
 *
 * @param noteId ID of the note to display
 * @param onBack Triggered when user navigates back
 */

@Composable
fun NoteDetailScreen(
    noteId: String,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // 🔥 États éditables
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // 👉 Simulation récupération note (plus tard ViewModel)
    LaunchedEffect(noteId) {
        // TODO: fetch note depuis ViewModel
        title = "Titre de la note"
        content = "Contenu de la note..."
    }

    fun saveNote() {
        if (title.isBlank() && content.isBlank()) return

        // TODO: update note via ViewModel
    }

    BackHandler {
        saveNote()
        onBack()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CreateNoteTopBar(
                scrollBehavior = scrollBehavior,
                title = title,
                onTitleChange = { title = it },
                onBackClick = {
                    saveNote()
                    onBack()
                },
                isNoteDetailsScreen = true
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize()
        ) {

            BasicTextField(
                value = content,
                onValueChange = { content = it },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                decorationBox = { innerTextField ->
                    if (content.isEmpty()) {
                        Text(
                            "Commencer à écrire...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}