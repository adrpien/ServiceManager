package com.example.feature_home_presentation.hospital_list_manager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HospitalListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _hospitalListState = mutableStateOf<List<Hospital>>(emptyList())
    val hospitalListState: State<List<Hospital>> = _hospitalListState


}

sealed class HospitalListManagerEvent() {
    data class DeleteHospital(val hospitalId: String): HospitalListManagerEvent()
    data class AddHospital(val hospitalName: String): HospitalListManagerEvent()

}