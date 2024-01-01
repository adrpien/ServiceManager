package com.example.servicemanager.feature_repairs_domain.use_cases


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_domain.R
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repairId: String): Flow<Resource<Repair>> {
        return try {
            repository.getRepair(repairId)
        } catch (e: IllegalArgumentException) {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        UiText.StringResource(R.string.unknown_error)
                    )
                )
            }
        }

    }

}