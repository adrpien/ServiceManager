package com.adrpien.tiemed.domain.use_case.inspections


import androidx.compose.ui.text.toLowerCase
import androidx.room.util.query
import com.adrpien.noteapp.feature_notes.domain.util.OrderMonotonicity
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_app.domain.model.Hospital
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
        hospitalFilter: Hospital? = null,
        searchQuery: String = "",
        orderType: OrderType = OrderType.State(OrderMonotonicity.Ascending),
        fetchFromApi: Boolean = false
    ): Flow<Resource<List<Inspection>>> {
        return if(fetchFromApi == false) {
            repository.getInspectionListFromLocal().map { resource ->
                resource.copy(
                    data = resource.data
                        ?.filter { inspection ->
                            inspection.toString().lowercase().contains(searchQuery.lowercase())
                        }
                        ?.filter { it.hospitalId == (hospitalFilter?.hospitalId ?: it.hospitalId) }
                        ?.orderInspectionList(orderType)
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
                        ?.orderInspectionList(orderType)
                )
                }
            }
        }
}
