package dev.geekpastor.mynote.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.geekpastor.mynote.R
import dev.geekpastor.mynote.ui.screens.login.components.ButtonGoogle
import dev.geekpastor.mynote.ui.theme.MyNoteTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.login_image),
            contentDescription = "Login Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
        )


        Text(
            text = "Notez tout ce qui vous vient à l'esprit" +
                    " et que vous souhaitez accomplir, aujourd'hui ou à l'avenir.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(top = 20.dp)
        )

        ButtonGoogle(
            modifier = Modifier
                .padding(top = 20.dp),
            text = {
                Text(
                    text = "Se conéctate con Google"
                )
            },
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    MyNoteTheme {
        LoginScreen()
    }
}