package dev.geekpastor.mynote.ui.screens.create.component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import dev.geekpastor.mynote.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onTitleChange: (String) -> Unit,
    onBackClick: () -> Unit,
    isNoteDetailsScreen : Boolean = false,
    isFavorite: Boolean = false,
    onToggleFavorite: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    LargeTopAppBar(
        title = {
            BasicTextField(
                value = title,
                onValueChange = onTitleChange,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text(
                            "Titre",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    innerTextField()
                }
            )
        },

        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrowback),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onToggleFavorite()
            }) {
                Icon(
                    painter = painterResource(
                        if (isFavorite)
                            R.drawable.ic_favorite_filled
                        else
                            R.drawable.ic_favorite_border
                    ),
                    contentDescription = "Favorite Toggle",
                    tint = if (isFavorite)
                        Color(0xFFE91E63) // rose style WhatsApp/Keep
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = "Back"
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_archive),
                    contentDescription = "Back"
                )
            }

            if (isNoteDetailsScreen){
                IconButton(onClick = {
                    onDeleteClick()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Delete Toggle"
                    )
                }
            }
        },

        scrollBehavior = scrollBehavior,

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}