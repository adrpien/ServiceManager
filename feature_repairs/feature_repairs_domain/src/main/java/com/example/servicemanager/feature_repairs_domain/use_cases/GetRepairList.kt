package com.example.servicemanager.feature_repairs_domain.use_cases


import com.example.servicemanager.feature_repairs_domain.util.RepairOrderMonotonicity
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import com.example.servicemanager.feature_repairs_domain.util.RepairListExtensionFunctions.Companion.orderRepairList
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetRepairList @Inject constructor (
    private val repository: RepairRepository
) {
    operator fun invoke(
        hospitalFilter: Hospital? = null,
        searchQuery: String = "",
        repairOrderType: RepairOrderType = RepairOrderType.State(RepairOrderMonotonicity.Ascending),
        fetchFromApi: Boolean = false
    ): Flow<com.example.core.util.Resource<List<Repair>>> {
        return if(fetchFromApi == false) {
            repository.getRepairListFromLocal().map { resource ->
                resource.copy(
                    data = resource.data
                        ?.filter { repair ->
                            repair.toString().lowercase().contains(searchQuery.lowercase())
                        }
                        ?.filter { it.hospitalId == (hospitalFilter?.hospitalId ?: it.hospitalId) }
                        ?.orderRepairList(repairOrderType)
                )
            }
        } else {
            repository.getRepairList().map { resource ->
                resource.copy(
                    data = resource.data
                        ?.filter { repair ->
                            repair.toString().lowercase().contains(searchQuery.lowercase())
                        }
                        ?.filter { it.hospitalId == (hospitalFilter?.hospitalId ?: it.hospitalId) }
                        ?.orderRepairList(repairOrderType)
                )
                }
            }
        }
}
