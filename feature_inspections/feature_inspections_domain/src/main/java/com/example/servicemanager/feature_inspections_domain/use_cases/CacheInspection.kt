package com.example.servicemanager.feature_inspections_domain.use_cases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_domain.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException
import javax.inject.Inject

class CacheInspection @Inject constructor (
    private val repository: InspectionRepository
) {
    suspend operator fun invoke(inspection: Inspection): Resource<Inspection> {

        return Resource (
            ResourceState.SUCCESS,
            inspection,
            UiText.DynamicString("empty implementation")
        )

    }
}