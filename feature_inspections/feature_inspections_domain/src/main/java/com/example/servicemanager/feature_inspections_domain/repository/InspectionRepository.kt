package com.example.servicemanager.feature_inspections_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.Flow

interface InspectionRepository {


    /* ***** Inspections ************************************************************************ */
    fun getInspection(inspectionId: String): Flow<com.example.core.util.Resource<Inspection>>
    fun getInspectionList(): Flow<com.example.core.util.Resource<List<Inspection>>>
    fun insertInspection(inspection: Inspection): Flow<com.example.core.util.Resource<String>>
    fun updateInspection(inspection: Inspection): Flow<com.example.core.util.Resource<String>>

    fun getInspectionListFromLocal(): Flow<com.example.core.util.Resource<List<Inspection>>>

}

