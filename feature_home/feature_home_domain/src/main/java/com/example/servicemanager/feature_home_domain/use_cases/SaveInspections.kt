package com.example.servicemanager.feature_home_domain.use_cases

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_domain.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveInspections @Inject constructor (
    private val repository: InspectionRepository
) {
    suspend operator fun invoke(inspectionList: List<Inspection>): Flow<Resource<String>> = flow {
        inspectionList.forEachIndexed() { index, inspection ->
            var isSuccessful = true
            val result = repository.insertInspection(inspection)
            when(result.resourceState) {
                ResourceState.SUCCESS -> {
                    emit(
                        Resource(
                            ResourceState.LOADING,
                            null,
                            UiText.DynamicString("Saved ${index} inspections")
                        )
                    )
                }
                ResourceState.ERROR -> {
                    isSuccessful = false
                }
                ResourceState.LOADING -> Unit
            }
            if (isSuccessful){
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        null,
                        UiText.DynamicString("Saving inspections finished successfully")
                    )
                )
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        UiText.DynamicString("Saving inspections error")
                    )
                )
            }

        }
    }
}
