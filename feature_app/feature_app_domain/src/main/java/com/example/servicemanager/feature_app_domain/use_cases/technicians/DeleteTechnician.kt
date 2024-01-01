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

class DeleteTechnician @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(technician: Technician): Resource<String> {
        return if(technician.technicianId != "") {
            repository.deleteTechnician(technician.technicianId)
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.technician_delete_unknown_error)
            )
        }
    }

}