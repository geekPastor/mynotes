package dev.geekpastor.mynote.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dev.geekpastor.mynote.R
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.ui.screens.home.components.MyTopAppBar
import dev.geekpastor.mynote.ui.screens.home.components.NoteItem
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
fun HomeScreenRoute(
    onNoteClick: (Note) -> Unit
){
    HomeScreen(onNoteClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNoteClick: (Note) -> Unit
){
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val notes = List(20) {
        Note(
            title = "Note ${it+1}",
            content = "Description ${"lorem ipsum ".repeat((1..5).random())}",
            id = "",
            createdAt = 0L,
            updatedAt = 0L
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(
            scrollBehavior.nestedScrollConnection
        ),
        topBar = {
            MyTopAppBar(scrollBehavior = scrollBehavior)
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
            HomeContent(
                onNoteClick = onNoteClick,
                notes = notes
            )
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