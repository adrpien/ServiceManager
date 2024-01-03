package com.example.servicemanager.feature_inspections_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.Flow

interface InspectionRepository {


    /* ***** Inspections ************************************************************************ */
    fun getInspection(inspectionId: String): Flow<Resource<Inspection>>
    fun getInspectionList(): Flow<Resource<List<Inspection>>>
    suspend fun insertInspection(inspection: Inspection): Resource<Inspection>
    suspend fun updateInspection(inspection: Inspection): Resource<Inspection>
    fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>>

}

