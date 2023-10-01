package com.example.servicemanager.feature_app.domain.repository

import com.example.servicemanager.feature_app.domain.model.*
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /* ***** Signatures ************************************************************************* */
    fun getSignature(signatureId: String): Flow<com.example.core.util.Resource<ByteArray>>
    fun updateSignature (signatureId: String, byteArray: ByteArray): Flow<com.example.core.util.Resource<String>>
    fun createSignature (signatureId: String, byteArray: ByteArray): Flow<com.example.core.util.Resource<String>>

    /* ***** Hospitals ************************************************************************** */
    fun getHospitalList(): Flow<com.example.core.util.Resource<List<Hospital>>>

    /* ***** Technicians ************************************************************************ */
    fun getTechnicianList(): Flow<com.example.core.util.Resource<List<Technician>>>

    /* ***** EstStates ************************************************************************** */
    fun getEstStateList(): Flow<com.example.core.util.Resource<List<EstState>>>

    /* ***** InspectionState ******************************************************************** */
    fun getInspectionStateList(): Flow<com.example.core.util.Resource<List<InspectionState>>>

    /* ***** RepairStates *********************************************************************** */
    fun getRepairStateList(): Flow<com.example.core.util.Resource<List<RepairState>>>

}