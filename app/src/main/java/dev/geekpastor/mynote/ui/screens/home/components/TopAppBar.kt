package dev.geekpastor.mynote.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.geekpastor.mynote.R


/**
 * Reusable Top App Bar component.
 *
 * Behavior:
 * - If [title] is null → Home mode (greeting + actions)
 * - If [title] is not null → Detail mode (back button + title)
 *
 * @param scrollBehavior Scroll behavior for collapsing effect
 * @param title Optional title (used in detail screens)
 * @param onBackClick Triggered when back arrow is clicked
 * @param onShareClick Triggered when share icon is clicked
 * @param onProfileClick Triggered when profile icon is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String? = null,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val isDetailScreen = title != null

    LargeTopAppBar(
        title = {
            if (isDetailScreen) {
                DetailTitle(title)
            } else {
                HomeTitle()
            }
        },

        navigationIcon = {
            if (isDetailScreen) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrowback),
                        contentDescription = "Back"
                    )
                }
            }
        },

        actions = {
            if (!isDetailScreen) {
                HomeActions(
                    onShareClick = onShareClick,
                    onProfileClick = onProfileClick
                )
            }
        },

        scrollBehavior = scrollBehavior,

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun HomeTitle() {
    Column {
        Text(
            text = "Salut 👋",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = stringResource(R.string.daily_note),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun DetailTitle(title: String) {
    Column {
        Text(
            text = "Note Details",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun HomeActions(
    onShareClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row {
        IconButton(onClick = onShareClick) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = "Share"
            )
        }

        IconButton(onClick = onProfileClick) {
            Icon(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Profile"
            )
        }
    }
}