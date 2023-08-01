package com.example.servicemanager.feature_user.presentation.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.compose.DefaultTextFieldState
import com.example.servicemanager.core.compose.components.DefaultTextField
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_user.presentation.login.UserLoginEvent
import com.example.servicemanager.feature_user.presentation.login.UserViewModel
import com.example.servicemanager.navigation.Navigation
import com.example.servicemanager.navigation.Screen

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: UserViewModel = hiltViewModel(),
) {

    val userLoginState = viewModel.userLoginState

    val userMail = remember {
        mutableStateOf(DefaultTextFieldState(
            hint = "Mail",
            value = userLoginState.value.mail
        ))
    }

    val userPassword = remember {
        mutableStateOf(DefaultTextFieldState(
            hint = "Password",
            value = userLoginState.value.password
        ))
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
            DefaultTextField(
                onValueChanged =  { password ->
                    userPassword.value= userPassword.value.copy(value = password)
                    viewModel.onEvent(UserLoginEvent.UpdateState(password = password, mail = userLoginState.value.mail))
                },
                state = userPassword
            )
            Button(
                // TODO Delete this screen from BackStack
                onClick = { navHostController.navigate(Screen.ContentComposable.route)}
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "button")
            }
        }
    }

}