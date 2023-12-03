package com.example.servicemanager.feature_authentication_presentation.login.components

import  androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_ui.components.other.DefaultButton
import com.example.core_ui.components.other.DefaultTextField
import com.example.core_ui.components.other.DefaultTextFieldState
import com.example.core_ui.components.other.PasswordTextField
import com.example.core.util.Screens
import com.example.servicemanager.feature_authentication_presentation.login.UiEvent
import com.example.servicemanager.feature_authentication_presentation.login.UserLoginEvent
import com.example.servicemanager.feature_authentication_presentation.login.UserLoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: UserLoginViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val userLoginState = viewModel.userLoginState

    val userPassword = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Password",
                value = userLoginState.value.password
            )
        )
    }
    val userMail = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Mail",
                value = userLoginState.value.mail
            )
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(UserLoginEvent.GetCurrentUser())
    }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.Authenticate -> {
                    navHostController.navigate(Screens.ContentComposable.route) {
                        popUpTo(Screens.UserLoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
                is UiEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.messege
                        )
                    }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                DefaultTextField(
                    onValueChanged = { mail ->
                        userMail.value = userMail.value.copy(value = mail)
                        viewModel.onEvent(
                            UserLoginEvent.UpdateState(
                                mail = mail,
                                password = userLoginState.value.password
                            )
                        )
                    },
                    state = userMail
                )
                PasswordTextField(
                    state = userPassword
                ) { password ->
                    userPassword.value = userPassword.value.copy(value = password)
                    viewModel.onEvent(
                        UserLoginEvent.UpdateState(
                            password = password,
                            mail = userLoginState.value.mail
                        )
                    )
                }
                DefaultButton(
                    title = "Authenticate",
                    onClick = {
                        viewModel.onEvent(
                            UserLoginEvent.Authenticate(
                                mail = userLoginState.value.mail,
                                password = userLoginState.value.password
                            )
                        )
                    }
                )

                Text(
                    text = "mail: adrpien@gmail.com"
                )
                Text(
                    text = "password: test12345"
                )

            }
        }
    }

}