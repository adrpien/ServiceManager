package com.example.servicemanager.feature_repairs_domain.use_cases


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

    operator fun invoke(repair: Repair): Flow<Resource<String>> {
        return if (repair.deviceSn.isNotEmpty() && repair.deviceIn.isNotEmpty())
        {
            repository.insertRepair(repair)
        } else {
        flow<Resource<String>> {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "TextFields deviceSn and deviceIn are empty",
                    "TextFields deviceSn and deviceIn are empty"
                )
            )
        }
        }
    }
}