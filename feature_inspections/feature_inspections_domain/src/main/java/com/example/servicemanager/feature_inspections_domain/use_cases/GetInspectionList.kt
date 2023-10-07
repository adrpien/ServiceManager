package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderMonotonicity
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_domain.util.InspectionListExtensionFunctions.Companion.orderInspectionList
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetInspectionList @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(
        hospitalFilter: Hospital? = null,
        searchQuery: String = "",
        inspectionOrderType: InspectionOrderType = InspectionOrderType.State(
            InspectionOrderMonotonicity.Ascending),
        fetchFromApi: Boolean = false
    ): Flow<com.example.core.util.Resource<List<Inspection>>> {
        return if(fetchFromApi == false) {
            repository.getInspectionListFromLocal().map { resource ->
                resource.copy(
                    data = resource.data
                        ?.filter { inspection ->
                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                        }
                        ?.filter { it.hospitalId == (hospitalFilter?.hospitalId ?: it.hospitalId) }
                        ?.orderInspectionList(inspectionOrderType)
                )
            }
        } else {
            repository.getInspectionList().map { resource ->
                resource.copy(
                    data = resource.data
                        ?.filter { inspection ->
                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                        }
                        ?.filter { it.hospitalId == (hospitalFilter?.hospitalId ?: it.hospitalId) }
                        ?.orderInspectionList(inspectionOrderType)
                )
                }
            }
        }
}
