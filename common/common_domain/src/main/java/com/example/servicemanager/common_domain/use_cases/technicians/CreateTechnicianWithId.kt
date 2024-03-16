package com.example.servicemanager.common_domain.use_cases.technicians

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.Technician
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class CreateTechnicianWithId @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(technician: Technician): Resource<String> {
        return try {
            repository.createTechnicianWithId(technician)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}