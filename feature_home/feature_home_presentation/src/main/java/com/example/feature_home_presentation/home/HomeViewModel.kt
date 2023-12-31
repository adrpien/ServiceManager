package com.example.feature_home_presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_authentication_domain.use_cases.AuthenticationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val appUseCases: AppUseCases,
    private val authenticationUseCases: AuthenticationUseCases
): ViewModel() {

    private var userId = ""

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        userId = savedStateHandle.get<String>("userId") ?: ""
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getUser(userId).collect() { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { user ->
                            _homeState.value = _homeState.value.copy(user = user )
                        }

                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }

        }
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.LogOut -> {
                viewModelScope.launch(Dispatchers.IO) {
                    authenticationUseCases.logOut()
                    _eventFlow.emit(UiEvent.FinishApp)
                }
            }
        }
    }
}

sealed class UiEvent() {

    object FinishApp: UiEvent()
    data class Navigate(val screen: Screen): UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}