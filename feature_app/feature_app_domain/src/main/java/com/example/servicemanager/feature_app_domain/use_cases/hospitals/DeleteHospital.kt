package com.example.servicemanager.feature_app_domain.use_cases.hospitals

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteHospital @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(hospital: Hospital): Flow<Resource<String>> {
        return if(hospital.hospitalId != "") {
            repository.deleteHospital(hospital.hospitalId)
        } else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Hospital delete unknown error",
                        UiText.StringResource(R.string.hospital_delete_unknown_error)
                    )
                )
            }
        }
    }

}