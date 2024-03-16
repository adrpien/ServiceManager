package com.example.servicemanager.feature_repairs_domain.use_cases


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderMonotonicity
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.common_domain.model.Hospital
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import com.example.servicemanager.feature_repairs_domain.util.RepairListExtensionFunctions.Companion.orderRepairList
import javax.inject.Inject

class GetRepairFilteredList @Inject constructor (
    private val repository: RepairRepository
) {
    operator fun invoke(
        repairList: List<Repair>,
        hospitalFilter: Hospital? = null,
        searchQuery: String = "",
        repairOrderType: RepairOrderType = RepairOrderType.State(RepairOrderMonotonicity.Ascending),
    ): Any {
        return Resource(
            resourceState = ResourceState.SUCCESS,
            data = repairList
                .filter { repair ->
                    repair.toString().lowercase().contains(searchQuery.lowercase())
                }
                .filter {
                    if (hospitalFilter?.hospitalId == "0") {
                        true
                    } else {
                        it.hospitalId == (hospitalFilter?.hospitalId ?: it.hospitalId)}
                }
                .orderRepairList(repairOrderType),
            message = null
            )
        }
}

