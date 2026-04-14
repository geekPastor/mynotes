package dev.geekpastor.mynote.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.ui.screens.create.component.CreateNoteTopBar
import dev.geekpastor.mynote.ui.screens.details.components.DeleteAlert
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class NoteDetailRoute(
    val noteId: String
) : NavKey

fun NavBackStack<NavKey>.navigateToNoteDetail(note: Note) {
    add(NoteDetailRoute(noteId = note.id))
}

@OptIn(ExperimentalMaterial3Api::class)
/**
 * Displays note details.
 *
 * @param noteId the ID of the note to display
 * @param onBack Triggered when user navigates back
 */

@Composable
fun NoteDetailScreen(
    noteId: String,
    onBack: () -> Unit,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    when (val state = uiState) {

        is NoteDetailUiState.Loading -> {
        // Loader simple
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is NoteDetailUiState.Error -> {
            val error = (uiState as NoteDetailUiState.Error).throwable
            Text(text = error.message ?: "Erreur")
        }

        is NoteDetailUiState.Success -> {

            NoteDetailContent(
                note = state.note,
                isSaving = state.isSaving,

                onTitleChange = viewModel::onTitleChange,
                onContentChange = viewModel::onContentChange,
                onToggleFavorite = viewModel::toggleFavorite,

                onDeleteClick = {
                    showDeleteDialog = true
                },

                onBack = {
                    viewModel.saveAndExit(onBack)
                }
            )
        }
    }

    if (showDeleteDialog) {
        DeleteAlert(
            onDismissRequest = { showDeleteDialog = false },
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteNote(onBack)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailContent(
    note: Note,
    isSaving: Boolean,

    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onToggleFavorite: () -> Unit,
    onDeleteClick: () -> Unit,
    onBack: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            CreateNoteTopBar(
                title = note.title,
                onTitleChange = onTitleChange,
                onBackClick = onBack,
                isFavorite = note.isFavorite,
                onToggleFavorite = onToggleFavorite,
                onDeleteClick = onDeleteClick,
                isNoteDetailsScreen = true,
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            if (isSaving) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            BasicTextField(
                value = note.content,
                onValueChange = onContentChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxSize(),
                decorationBox = { innerTextField ->
                    if (note.content.isEmpty()) {
                        Text(
                            "Commencer à écrire...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}