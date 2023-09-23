package com.example.servicemanager.feature_inspections.domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.Flow

interface InspectionRepository {


    /* ***** Inspections ************************************************************************ */
    fun getInspection(inspectionId: String): Flow<Resource<Inspection>>
    fun getInspectionList(): Flow<Resource<List<Inspection>>>
    fun insertInspection(inspection: Inspection): Flow<Resource<String>>
    fun updateInspection(inspection: Inspection): Flow<Resource<String>>

    fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>>

}

