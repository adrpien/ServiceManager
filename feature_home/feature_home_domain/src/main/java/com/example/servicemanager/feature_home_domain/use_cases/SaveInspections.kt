package com.example.servicemanager.feature_home_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveInspections @Inject constructor (
    private val repository: InspectionRepository
) {
    suspend operator fun invoke(inspectionList: List<Inspection>): Flow<Resource<String>> = flow {
        var isSuccessful = true
        inspectionList.forEachIndexed { index, inspection ->
            val result = repository.insertInspection(inspection)
            when(result.resourceState) {
                ResourceState.SUCCESS -> {
                    emit(
                        Resource(
                            ResourceState.LOADING,
                            null,
                            UiText.StringResource(
                                id = com.example.feature_home_domain.R.string.saved_x_inspections,
                                args = listOf<String>(
                                    (index + 1).toString()
                                )
                            )
                        )
                    )
                }
                ResourceState.ERROR -> {
                    isSuccessful = false
                }
                ResourceState.LOADING -> Unit
            }
        }
        if (isSuccessful){
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(com.example.feature_home_domain.R.string.saving_inspections_finished_successfully)
                )
            )
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(com.example.feature_home_domain.R.string.saving_inspections_error)
                )
            )
        }
    }
}
