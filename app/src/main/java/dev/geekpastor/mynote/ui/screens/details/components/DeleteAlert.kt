package dev.geekpastor.mynote.ui.screens.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteAlert(
    onDismissRequest: ()-> Unit = {},
    onConfirm: ()-> Unit = {}
){
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Text(
                text = "Supprimer",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onConfirm()
                    }
            )
        },
        dismissButton = {
            Text(
                text = "Annuler",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onDismissRequest()
                    }
            )
        },
        title = { Text("Supprimer la note") },
        text = { Text("Es-tu sûr de vouloir supprimer cette note ?") }
    )
}