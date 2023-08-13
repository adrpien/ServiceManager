package com.example.servicemanager.feature_user.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.compose.DefaultTextFieldState
import com.example.servicemanager.core.compose.components.DefaultButton
import com.example.servicemanager.core.compose.components.DefaultTextField
import com.example.servicemanager.core.compose.components.PasswordTextField
import com.example.servicemanager.feature_user.presentation.login.UserLoginEvent
import com.example.servicemanager.feature_user.presentation.login.UserLoginViewModel
import com.example.servicemanager.navigation.Screen
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige
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
        mutableStateOf(DefaultTextFieldState(
            hint = "Password",
            value = userLoginState.value.password
        ))
    }
    val userMail = remember {
        mutableStateOf(DefaultTextFieldState(
            hint = "Mail",
            value = userLoginState.value.mail
        ))
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(UserLoginEvent.GetCurrentUser())
    }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UserLoginViewModel.UiEvent.Authenticate -> {
                    navHostController.navigate(Screen.ContentComposable.route) {
                        popUpTo(Screen.UserLoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
                is UserLoginViewModel.UiEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.messege
                        )
                    }
                is UserLoginViewModel.UiEvent.NavigateTo -> {
                        navHostController.navigate(Screen.ContentComposable.route) {
                            popUpTo(Screen.UserLoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(TiemedVeryLightBeige)
            .padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                DefaultTextField(
                    onValueChanged =  { mail ->
                        userMail.value= userMail.value.copy(value = mail)
                        viewModel.onEvent(UserLoginEvent.UpdateState(mail = mail, password =  userLoginState.value.password))
                    },
                    state = userMail
                )
                PasswordTextField(
                    state = userPassword
                ) { password ->
                    userPassword.value= userPassword.value.copy(value = password)
                    viewModel.onEvent(UserLoginEvent.UpdateState(
                        password = password,
                        mail = userLoginState.value.mail
                    )
                    )
                }
                DefaultButton(
                    title = "Authenticate",
                    onClick = {
                        viewModel.onEvent(UserLoginEvent.Authenticate(
                            mail = userLoginState.value.mail,
                            password = userLoginState.value.password))
                    }
                )

            }
        }
    }

}