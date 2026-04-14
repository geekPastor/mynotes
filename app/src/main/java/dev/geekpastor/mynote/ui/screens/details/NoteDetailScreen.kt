package dev.geekpastor.mynote.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.ui.screens.create.component.CreateNoteTopBar
import kotlinx.coroutines.launch
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
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 🔥 Init une seule fois
    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    when (uiState) {

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

            val state = uiState as NoteDetailUiState.Success
            val currentNote = state.note

            if (state.isSaving) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            // 👉 TON UI PRINCIPAL
            NoteDetailContent(
                note = currentNote,
                onTitleChange = viewModel::onTitleChange,
                onContentChange = viewModel::onContentChange,
                onBack = {
                    viewModel.saveNote()
                    onBack()
                },
                scrollBehavior = scrollBehavior
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailContent(
    note: Note,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {

    BackHandler {
        onBack()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CreateNoteTopBar(
                scrollBehavior = scrollBehavior,
                title = note.title,
                onTitleChange = onTitleChange,
                onBackClick = onBack,
                isNoteDetailsScreen = true
            )
        }
    ) { paddingValues ->

        val scrollState = rememberScrollState()
        val bringIntoViewRequester = remember { BringIntoViewRequester() }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding()
        ) {

            BasicTextField(
                value = note.content,
                onValueChange = {
                    onContentChange(it)

                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                decorationBox = { innerTextField ->
                    if (note.content.isEmpty()) {
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