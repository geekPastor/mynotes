package dev.geekpastor.mynote.ui.screens.login

import android.app.Activity
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dev.geekpastor.mynote.R
import dev.geekpastor.mynote.ui.screens.login.components.ButtonGoogle
import dev.geekpastor.mynote.ui.theme.MyNoteTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Serializable
data object LoginRoute: NavKey


fun NavBackStack<NavKey>.navigateToLogin(){
    if (lastOrNull() !is LoginRoute){
        clear()
        add(LoginRoute)
    }
}

@Composable
fun LoginScreenRoute(
    viewModel: LoginViewModel = koinViewModel(),
    navigateHome: ()-> Unit = {},
    webClientToken: String
){
    val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = loginUiState,
        webClientToken = webClientToken,
        onLoginWithGoogle = {credential->
            viewModel.signInWithGoogle(credential = credential)
            navigateHome()
        },
        startAuthFlow = {
            viewModel.startAuthFlow()
        }
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    webClientToken: String = "",
    uiState: LoginUiState = LoginUiState.Idle,
    onLoginWithGoogle: (credential: AuthCredential) -> Unit = {},
    startAuthFlow: () -> Unit = {}
){

    val context = LocalContext.current


    val oneTapClient = remember {
        Identity.getSignInClient(context)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                onLoginWithGoogle(credential)
            }catch (e: ApiException){
                Log.e("Auth Screen", "signInResult:failed", e)
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    val oneTapLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                try {
                    val signInCredential = oneTapClient.getSignInCredentialFromIntent(it.data)
                    val credential = GoogleAuthProvider
                        .getCredential(signInCredential.googleIdToken, null)
                    onLoginWithGoogle(credential)
                }catch (e: ApiException){
                    Log.e("Auth Screen", "signInResult:failed", e)
                }
        }

    LaunchedEffect(Unit) {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(webClientToken)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(context as Activity) { result->
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()

                try {
                    oneTapLauncher.launch(intentSenderRequest)
                }catch (e: Exception){

                }
            }
    }


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


        AnimatedContent(targetState = uiState, label = "") {
            when (it) {
                LoginUiState.Idle -> ButtonGoogle(
                    onClick = {
                        startAuthFlow()
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(webClientToken)
                                .requestEmail()
                                .build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        googleSignInClient.revokeAccess()

                        launcher.launch(googleSignInClient.signInIntent)
                    },
                    modifier = Modifier.padding(top = 20.dp)
                )

                LoginUiState.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {}
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview(){
    MyNoteTheme {
        LoginScreen()
    }
}