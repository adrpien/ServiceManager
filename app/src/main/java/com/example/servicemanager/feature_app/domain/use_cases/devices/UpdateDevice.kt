package com.example.servicemanager.feature_app.domain.use_cases.devices


import com.example.servicemanager.feature_app.domain.model.Device
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDevice @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(device: Device): Flow<Resource<Boolean>> {
        return repository.updateDevice(device)
    }
}