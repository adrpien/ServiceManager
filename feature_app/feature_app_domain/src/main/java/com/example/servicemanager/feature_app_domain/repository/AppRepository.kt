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
    suspend fun updateSignature (signatureId: String, byteArray: ByteArray): Resource<String>
    suspend fun createSignature (signatureId: String, byteArray: ByteArray): Resource<String>

    /* ***** Hospitals ************************************************************************** */
    fun getHospitalList(): Flow<Resource<List<Hospital>>>
    suspend fun updateHospital(hospital: Hospital): Resource<String>
    suspend fun createHospital(hospital: Hospital): Resource<String>
    suspend fun createHospitalWithId(hospital: Hospital): Resource<String>
    suspend fun deleteHospital(hospitalId: String): Resource<String>

    /* ***** Technicians ************************************************************************ */
    fun getTechnicianList(): Flow<Resource<List<Technician>>>
    suspend fun updateTechnician(technician: Technician): Resource<String>
    suspend fun createTechnician(technician: Technician): Resource<String>
    suspend fun deleteTechnician(technicianId: String): Resource<String>

    /* ***** EstStates ************************************************************************** */
    fun getEstStateList(): Flow<Resource<List<EstState>>>
    suspend fun updateEstState(estState: EstState): Resource<String>
    suspend fun createEstState(estState: EstState): Resource<String>
    suspend fun deleteEstState(estStateId: String): Resource<String>


    /* ***** InspectionState ******************************************************************** */
    fun getInspectionStateList(): Flow<Resource<List<InspectionState>>>
    suspend fun updateInspectionState(inspectionState: InspectionState): Resource<String>
    suspend fun createInspectionState(inspectionState: InspectionState): Resource<String>
    suspend fun deleteInspectionState(inspectionStateId: String): Resource<String>


    /* ***** RepairStates *********************************************************************** */
    fun getRepairStateList(): Flow<Resource<List<RepairState>>>
    suspend fun updateRepairState(repairState: RepairState): Resource<String>
    suspend fun createRepairState(repairState: RepairState): Resource<String>
    suspend fun deleteRepairState(repairStateId: String): Resource<String>


    /* ***** User ******************************************************************************* */
    fun getUser(userId: String): Flow<Resource<User>>

    /* ***** User Types ************************************************************************* */

    fun getUserTypeList(): Flow<Resource<List<UserType>>>
    suspend fun updateUserType(userType: UserType): Resource<String>
    suspend fun createUserType(userType: UserType): Resource<String>
    suspend fun deleteUserType(userTypeId: String): Resource<String>



}