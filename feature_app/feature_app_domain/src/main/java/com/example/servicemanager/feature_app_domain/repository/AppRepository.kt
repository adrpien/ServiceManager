package com.example.servicemanager.feature_app_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_app_domain.model.UserType
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    /* ***** Signatures ************************************************************************* */
    fun getSignature(signatureId: String): Flow<Resource<ByteArray>>
    fun updateSignature (signatureId: String, byteArray: ByteArray): Flow<Resource<String>>
    fun createSignature (signatureId: String, byteArray: ByteArray): Flow<Resource<String>>

    /* ***** Hospitals ************************************************************************** */
    fun getHospitalList(): Flow<Resource<List<Hospital>>>
    fun updateHospital(hospital: Hospital): Flow<Resource<String>>
    fun createHospital(hospital: Hospital): Flow<Resource<String>>
    fun createHospitalWithId(hospital: Hospital): Flow<Resource<String>>
    fun deleteHospital(hospitalId: String): Flow<Resource<String>>

    /* ***** Technicians ************************************************************************ */
    fun getTechnicianList(): Flow<Resource<List<Technician>>>
    fun updateTechnician(technician: Technician): Flow<Resource<String>>
    fun createTechnician(technician: Technician): Flow<Resource<String>>
    fun deleteTechnician(technicianId: String): Flow<Resource<String>>

    /* ***** EstStates ************************************************************************** */
    fun getEstStateList(): Flow<Resource<List<EstState>>>
    fun updateEstState(estState: EstState): Flow<Resource<String>>
    fun createEstState(estState: EstState): Flow<Resource<String>>
    fun deleteEstState(estStateId: String): Flow<Resource<String>>


    /* ***** InspectionState ******************************************************************** */
    fun getInspectionStateList(): Flow<Resource<List<InspectionState>>>
    fun updateInspectionState(inspectionState: InspectionState): Flow<Resource<String>>
    fun createInspectionState(inspectionState: InspectionState): Flow<Resource<String>>
    fun deleteInspectionState(inspectionStateId: String): Flow<Resource<String>>


    /* ***** RepairStates *********************************************************************** */
    fun getRepairStateList(): Flow<Resource<List<RepairState>>>
    fun updateRepairState(repairState: RepairState): Flow<Resource<String>>
    fun createRepairState(repairState: RepairState): Flow<Resource<String>>
    fun deleteRepairState(repairStateId: String): Flow<Resource<String>>


    /* ***** User ******************************************************************************* */
    fun getUser(userId: String): Flow<Resource<User>>

    /* ***** User Types ************************************************************************* */

    fun getUserTypeList(): Flow<Resource<List<UserType>>>
    fun updateUserType(userType: UserType): Flow<Resource<String>>
    fun createUserType(userType: UserType): Flow<Resource<String>>
    fun deleteUserType(userTypeId: String): Flow<Resource<String>>



}