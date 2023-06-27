package com.example.servicemanager.feature_app.domain.use_cases.devices


import com.example.servicemanager.feature_app.domain.model.Device
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDevice @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(deviceId: String): Flow<Resource<Device>> {
        if(deviceId.isBlank()) {
            return flow {  }
        }
        return repository.getDevice(deviceId)
    }

}