package com.example.servicemanager.feature_app_domain.use_cases.technicians

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import javax.inject.Inject

class UpdateTechnician @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(technician: Technician): Resource<String> {
        return try {
            repository.updateTechnician(technician)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.technician_update_unknown_error)
            )
        }
    }

}