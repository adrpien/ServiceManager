package com.adrpien.tiemed.domain.use_case.repairs


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repair: Repair): Flow<com.example.core.util.Resource<String>> {
        return if (repair.deviceSn.isNotEmpty() && repair.deviceIn.isNotEmpty())
        {
            repository.insertRepair(repair)
        } else {
        flow<com.example.core.util.Resource<String>> {
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.ERROR,
                    "TextFields deviceSn and deviceIn are empty",
                    "TextFields deviceSn and deviceIn are empty"
                )
            )
        }
        }
    }
}