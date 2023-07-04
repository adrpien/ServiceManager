package com.adrpien.tiemed.domain.use_case.inspections


import androidx.compose.ui.text.toLowerCase
import androidx.room.util.query
import com.adrpien.noteapp.feature_notes.domain.util.OrderMonotonicity
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections.domain.util.ListExtensionFunctions.Companion.orderInspectionList
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetInspectionList @Inject constructor (
    private val repository: InspectionRepository
) {

    // TODO GetInspectionList use case look really messy, need to work on it
    operator fun invoke(
        searchQuery: String = "",
        orderType: OrderType = OrderType.State(OrderMonotonicity.Ascending),
        fetchFromApi: Boolean = false
    ): Flow<Resource<List<Inspection>>> {
        return if(fetchFromApi == false) {
            repository.getInspectionListFromLocal().map { resource ->
                when(orderType.orderMonotonicity) {
                    is OrderMonotonicity.Ascending -> {
                        when(orderType) {
                            is OrderType.Date -> {
                                resource.copy(
                                    data = resource.data
                                        ?.filter { inspection ->
                                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                                        }
                                        ?.sortedBy { inspection -> inspection.inspectionDate }
                                )
                            }
                            is OrderType.Hospital -> {
                                resource.copy(
                                    data = resource.data
                                        ?.filter { inspection ->
                                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                                        }
                                        ?.sortedBy { inspection -> inspection.hospitalId }
                                )
                            }
                            is OrderType.State -> {
                                resource.copy(
                                    data = resource.data
                                        ?.filter { inspection ->
                                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                                        }
                                        ?.sortedBy { inspection -> inspection.inspectionStateId }
                                )
                            }
                        }

                    }
                    is OrderMonotonicity.Descending -> {
                        when(orderType) {
                            is OrderType.Date -> {
                                resource.copy(
                                    data = resource.data
                                        ?.filter { inspection ->
                                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                                        }
                                        ?.sortedByDescending { inspection -> inspection.inspectionDate }
                                )
                            }
                            is OrderType.Hospital -> {
                                resource.copy(
                                    data = resource.data
                                        ?.filter { inspection ->
                                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                                        }
                                        ?.sortedByDescending { inspection -> inspection.hospitalId }
                                )
                            }
                            is OrderType.State -> {
                                resource.copy(
                                    data = resource.data
                                        ?.filter { inspection ->
                                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                                        }
                                        ?.sortedByDescending { inspection -> inspection.inspectionStateId }
                                )
                            }
                        }

                    }
                }
            }
        } else {
            repository.getInspectionList().map { resource ->
                resource.copy(
                    data = resource.data
                        ?.filter { inspection ->
                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                        }
                        ?.orderInspectionList(orderType)
                )
                }
            }
        }
}
