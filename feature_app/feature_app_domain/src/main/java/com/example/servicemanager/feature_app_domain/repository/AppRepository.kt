package com.example.servicemanager.feature_app_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
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

    /* ***** User ******************************************************************************* */
    fun getUser(id: String): Flow<Resource<User>>

}