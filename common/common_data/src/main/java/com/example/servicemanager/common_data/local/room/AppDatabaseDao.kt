package com.example.servicemanager.common_data.local.room

import androidx.room.*
import com.example.servicemanager.common_data.local.room.entities.CachedSignatureEntity
import com.example.servicemanager.common_data.local.room.entities.EstStateEntity
import com.example.servicemanager.common_data.local.room.entities.HospitalEntity
import com.example.servicemanager.common_data.local.room.entities.InspectionStateEntity
import com.example.servicemanager.common_data.local.room.entities.RepairStateEntity
import com.example.servicemanager.common_data.local.room.entities.TechnicianEntity
import com.example.servicemanager.common_data.local.room.entities.UserTypeEntity
import com.example.servicemanager.common_domain.model.UserType


@Dao
interface AppDatabaseDao {

    /* ***** Cached Signatures ****************************************************************** */
    @Query("SELECT * FROM cachedsignatureentity")
    suspend fun getCachedSignatureList(): List<CachedSignatureEntity>
    @Query("DELETE FROM cachedsignatureentity WHERE signatureId LIKE :signatureId")
    suspend fun deleteCachedSignature(signatureId: String)
    @Query("DELETE FROM cachedsignatureentity")
    suspend fun deleteAllCachedSignatures()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedSignature(cachedSignatureEntity: CachedSignatureEntity)
    @Query("SELECT * FROM cachedsignatureentity WHERE signatureId LIKE :signatureId")
    suspend fun getCachedSignature(signatureId: String): CachedSignatureEntity

    /* ***** Hospitals ************************************************************************** */
    @Query("SELECT * FROM hospitalentity")
    suspend fun getHospitalList(): List<HospitalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospital(hospitalEntity: HospitalEntity)

    @Transaction
    @Query("SELECT * FROM hospitalentity WHERE hospitalId LIKE :hospitalId")
    suspend fun getHospital(hospitalId: String): HospitalEntity

    @Transaction
    @Query("DELETE FROM hospitalentity WHERE hospitalId LIKE :hospitalId")
    suspend fun deleteHospital(hospitalId: String)

    @Query("DELETE FROM hospitalentity")
    suspend fun deleteAllHospitals()

    /* ***** Technicians ************************************************************************ */
    @Query("SELECT * FROM technicianentity")
    suspend fun getTechnicianList(): List<TechnicianEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnician(technicianEntity: TechnicianEntity)

    @Transaction
    @Query("SELECT * FROM technicianentity WHERE technicianId LIKE :technicianId")
    suspend fun getTechnician(technicianId: String): TechnicianEntity

    @Transaction
    @Query("DELETE FROM technicianentity WHERE technicianId LIKE :technicianId")
    suspend fun deleteTechnician(technicianId: String)

    @Query("DELETE FROM technicianentity")
    suspend fun deleteAllTechnicians()

    /* ***** EstStates ************************************************************************** */
    @Query("SELECT * FROM eststateentity")
    suspend fun getEstStateList(): List<EstStateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstState(estState: EstStateEntity)

    @Transaction
    @Query("SELECT * FROM eststateentity WHERE estStateId LIKE :estStateId")
    suspend fun getEstState(estStateId: String): EstStateEntity

    @Transaction
    @Query("DELETE FROM eststateentity WHERE estStateId LIKE :estStateId")
    suspend fun deleteEstState(estStateId: String)

    @Query("DELETE FROM eststateentity")
    suspend fun deleteAllEstStates()

    /* ***** InspectionState ******************************************************************** */
    @Query("SELECT * FROM inspectionstateentity")
    suspend fun getInspectionStateList(): List<InspectionStateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspectionState(inspectionState: InspectionStateEntity)

    @Transaction
    @Query("SELECT * FROM inspectionstateentity WHERE inspectionStateId LIKE :inspectionStateId")
    suspend fun getInspectionState(inspectionStateId: String): InspectionStateEntity

    @Transaction
    @Query("DELETE FROM inspectionstateentity WHERE inspectionStateId LIKE :inspectionStateId")
    suspend fun deleteInspectionState(inspectionStateId: String)

    @Query("DELETE FROM inspectionstateentity")
    suspend fun deleteAllInspectionStates()

    /* ***** RepairStates *********************************************************************** */
    @Query("SELECT * FROM repairstateentity")
    suspend fun getRepairStateList(): List<RepairStateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepairState(repairState: RepairStateEntity)

    @Transaction
    @Query("SELECT * FROM repairstateentity WHERE repairStateId LIKE :repairStateId")
    suspend fun getRepairState(repairStateId: String): RepairStateEntity

    @Transaction
    @Query("DELETE FROM repairstateentity WHERE repairStateId LIKE :repairStateId")
    suspend fun deleteRepairState(repairStateId: String)

    @Query("DELETE FROM repairstateentity")
    suspend fun deleteAllRepairStates()

    /* ***** UserTypes *********************************************************************** */
    @Query("SELECT * FROM usertypeentity")
    suspend fun getUserTypeList(): List<UserTypeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserType(userType: UserTypeEntity)

    @Transaction
    @Query("SELECT * FROM usertypeentity WHERE userTypeId LIKE :userTypeId")
    suspend fun getUserType(userTypeId: String): UserType

    @Transaction
    @Query("DELETE FROM usertypeentity WHERE userTypeId LIKE :userTypeId")
    suspend fun deleteUserType(userTypeId: String)

    @Query("DELETE FROM usertypeentity")
    suspend fun deleteAllUserTypes()

}