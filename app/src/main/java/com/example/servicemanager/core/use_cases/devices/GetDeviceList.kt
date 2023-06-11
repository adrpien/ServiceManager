package com.adrpien.tiemed.domain.use_case.devices

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.Device
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeviceList @Inject constructor (
    private val repository: TiemedRepository
) {

    operator fun invoke(): Flow<Resource<List<Device>>> {
        return repository.getDeviceList()
    }
}