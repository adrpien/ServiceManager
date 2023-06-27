package com.example.servicemanager.feature_inspections.domain.repository

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import kotlinx.coroutines.flow.Flow

interface InspectionRepository {


    /* ***** Inspections ************************************************************************ */
    fun getInspection(inspectionId: String): Flow<Resource<Inspection>>
    fun getInspectionList(): Flow<Resource<List<Inspection>>>
    fun insertInspection(inspection: Inspection): Flow<Resource<Boolean>>
    fun updateInspection(inspection: Inspection): Flow<Resource<Boolean>>

    fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>>

}

