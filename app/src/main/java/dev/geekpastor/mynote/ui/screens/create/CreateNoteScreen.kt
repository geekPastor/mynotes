package dev.geekpastor.mynote.ui.screens.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.ui.screens.create.component.CreateNoteTopBar
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Serializable
data object CreateNoteRoute: NavKey

fun NavBackStack<NavKey>.navigateToCreateNote(){
    if (lastOrNull() !is CreateNoteRoute){
        add(CreateNoteRoute)
    }
}

@Composable
fun CreateNoteRoute(
    onBack: () -> Unit,
    viewModel: CreateNoteViewModel = koinViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreateNoteScreen(
        onBack = onBack,
        onCreateNoteClicked = { note ->
            viewModel.createNote(note)
        }
    )

    // 🔥 Gestion du succès
    LaunchedEffect(uiState) {
        if (uiState is CreateNoteUiState.Success) {
            onBack()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    onCreateNoteClicked: (Note) -> Unit = {},
    onBack: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    fun saveNote() {
        if (title.isBlank() && content.isBlank()) return

        onCreateNoteClicked(
            Note(
                title = title,
                content = content
            )
        )
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
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
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