package com.adrpien.tiemed.domain.use_case.room

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.Device
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRoomDeviceList @Inject constructor(
    private val tiemedRepository: TiemedRepository
) {

    operator fun invoke(deviceList: List<Device>): Flow<Resource<Boolean>> {
        return tiemedRepository.updateRoomDeviceList(deviceList)
    }
}