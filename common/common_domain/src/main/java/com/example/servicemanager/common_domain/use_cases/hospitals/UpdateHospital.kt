package com.example.servicemanager.common_domain.use_cases.hospitals

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.Hospital
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class UpdateHospital @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(hospital: Hospital): Resource<String> {
        return try {
            repository.updateHospital(hospital)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}