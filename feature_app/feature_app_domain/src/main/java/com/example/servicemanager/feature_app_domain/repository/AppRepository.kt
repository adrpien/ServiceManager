package com.example.servicemanager.feature_app.domain.repository

import com.example.servicemanager.feature_app.domain.model.*
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /* ***** Signatures ************************************************************************* */
    fun getSignature(signatureId: String): Flow<Resource<ByteArray>>
    fun updateSignature (signatureId: String, byteArray: ByteArray): Flow<Resource<String>>
    fun createSignature (signatureId: String, byteArray: ByteArray): Flow<Resource<String>>

    /* ***** Hospitals ************************************************************************** */
    fun getHospitalList(): Flow<Resource<List<Hospital>>>

    /* ***** Technicians ************************************************************************ */
    fun getTechnicianList(): Flow<Resource<List<Technician>>>

    /* ***** EstStates ************************************************************************** */
    fun getEstStateList(): Flow<Resource<List<EstState>>>

    /* ***** InspectionState ******************************************************************** */
    fun getInspectionStateList(): Flow<Resource<List<InspectionState>>>

    /* ***** RepairStates *********************************************************************** */
    fun getRepairStateList(): Flow<Resource<List<RepairState>>>

}