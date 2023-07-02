package com.adrpien.tiemed.domain.use_case.inspections


import androidx.room.util.query
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetInspectionList @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(
        searchQuery: String = "",
        fetchFromApi: Boolean = false
    ): Flow<Resource<List<Inspection>>> {
        return if(fetchFromApi == false) {
            repository.getInspectionListFromLocal().map { resource ->
                resource.copy(
                    data = resource.data?.filter { inspection ->
                        inspection.toString().contains(searchQuery)
                    }
                )
            }
        } else {
            repository.getInspectionList().map { resource ->
                resource.copy(
                    data = resource.data?.filter { inspection ->
                        inspection.toString().contains(searchQuery)
                    }
                )
            }
        }
    }
}
