package dev.geekpastor.mynote.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.R
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.ui.screens.home.components.MyTopAppBar
import dev.geekpastor.mynote.ui.screens.home.components.NoteItem
import dev.geekpastor.mynote.utils.paddingAndConsumeWindowInsets
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object HomeRoute: NavKey

fun NavBackStack<NavKey>.navigateToHome(){
    if (lastOrNull() !is HomeRoute){
        clear()
        add(HomeRoute)
    }
}

@Composable
fun HomeScreenRoute(
    onNoteClick: (Note) -> Unit,
    navigateToCreateNote: () -> Unit,
    viewModel: HomeViewModel = koinViewModel ()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        onNoteClicked = onNoteClick,
        navigateToCreateNote = navigateToCreateNote,
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNoteClicked: (Note) -> Unit,
    navigateToCreateNote: ()-> Unit,
    uiState: HomeUiState
){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            MyTopAppBar(scrollBehavior = scrollBehavior)
        },
        bottomBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToCreateNote
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_create),
                    contentDescription = "Create note Icon"
                )
            }
        }
    ) {paddingValues ->
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = {},
            modifier = Modifier
                .fillMaxSize()
                .paddingAndConsumeWindowInsets(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when(uiState){
                HomeUiState.Empty ->{
                }

                is HomeUiState.Error -> {
                    Log.d("Error: ", "Une erreur est survenue")
                    Text(
                        text = "Une erreur est survenue"
                    )
                }

                HomeUiState.Loading->{
                    CircularProgressIndicator()
                }

                is HomeUiState.Success -> {
                    if (uiState.notes.isNotEmpty()){

                        HomeContent(
                            onNoteClick = onNoteClicked,
                            notes = uiState.notes
                        )

                    }else{
                        Text(
                            text = "Aucune note pour le moment"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HomeContent(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchBar()

        Spacer(modifier = Modifier.height(12.dp))

        NotesGrid(
            notes = notes,
            onNoteClick = onNoteClick
        )
    }
}


//Search Bar
@Composable
private fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        placeholder = { Text("Search notes...") }
    )
}


//Note Grid list
@Composable
private fun NotesGrid(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes.size) { index ->
            val note = notes[index]

            NoteItem(
                note = note,
                onClick = { onNoteClick(note) }
            )
        }
    }
}