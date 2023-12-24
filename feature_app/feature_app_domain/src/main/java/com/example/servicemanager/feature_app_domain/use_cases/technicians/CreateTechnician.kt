package com.example.servicemanager.feature_app_domain.use_cases.technicians

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateTechnician @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(technician: Technician): Flow<Resource<String>> {
        return if(technician.technicianId != "") {
            repository.createTechnician(technician)
        } else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Technician name create can not be empty",
                        UiText.StringResource(R.string.technician_name_can_not_be_empty)
                    )
                )
            }
        }
    }

}