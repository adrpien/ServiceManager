package com.example.servicemanager.feature_app_domain.use_cases

import com.example.servicemanager.feature_app_domain.use_cases.clipboard.CopyToClipboard
import com.example.servicemanager.feature_app_domain.use_cases.date_formatting_types.GetDateFormattingTypes
import com.example.servicemanager.feature_app_domain.use_cases.est_states.CreateEstState
import com.example.servicemanager.feature_app_domain.use_cases.est_states.CreateEstStateWithId
import com.example.servicemanager.feature_app_domain.use_cases.est_states.DeleteEstState
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app_domain.use_cases.signatures.SaveSignature
import com.example.servicemanager.feature_app_domain.use_cases.signatures.GetSignature
import com.example.servicemanager.feature_app_domain.use_cases.signatures.UpdateSignature
import com.example.servicemanager.feature_app_domain.use_cases.est_states.GetEstStateList
import com.example.servicemanager.feature_app_domain.use_cases.est_states.UpdateEstState
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.CreateHospital
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.CreateHospitalWithId
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.DeleteHospital
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.UpdateHospital
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.CreateInspectionState
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.CreateInspectionStateWithId
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.DeleteInspectionState
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.GetInspectionStateList
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.UpdateInspectionState
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.CreateRepairState
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.CreateRepairStateWithId
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.DeleteRepairState
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.GetRepairStateList
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.UpdateRepairState
import com.example.servicemanager.feature_app_domain.use_cases.sync_data.SyncData
import com.example.servicemanager.feature_app_domain.use_cases.technicians.CreateTechnician
import com.example.servicemanager.feature_app_domain.use_cases.technicians.CreateTechnicianWithId
import com.example.servicemanager.feature_app_domain.use_cases.technicians.DeleteTechnician
import com.example.servicemanager.feature_app_domain.use_cases.technicians.GetTechnicianList
import com.example.servicemanager.feature_app_domain.use_cases.technicians.UpdateTechnician
import com.example.servicemanager.feature_app_domain.use_cases.user_types.CreateUserType
import com.example.servicemanager.feature_app_domain.use_cases.user_types.CreateUserTypeWithId
import com.example.servicemanager.feature_app_domain.use_cases.user_types.DeleteUserType
import com.example.servicemanager.feature_app_domain.use_cases.user_types.GetUserTypeList
import com.example.servicemanager.feature_app_domain.use_cases.user_types.UpdateUserType
import com.example.servicemanager.feature_app_domain.use_cases.users.GetUser

data class AppUseCases(
    val saveSignature: SaveSignature,
    val getSignature: GetSignature,
    val updateSignature: UpdateSignature,
    val getHospitalList: GetHospitalList,
    val createHospital: CreateHospital,
    val createHospitalWithId: CreateHospitalWithId,
    val updateHospital: UpdateHospital,
    val deleteHospital: DeleteHospital,
    val getEstStateList: GetEstStateList,
    val createEstState: CreateEstState,
    val createEstStateWithId: CreateEstStateWithId,
    val updateEstState: UpdateEstState,
    val deleteEstState: DeleteEstState,
    val getInspectionStateList: GetInspectionStateList,
    val createInspectionState: CreateInspectionState,
    val createInspectionStateWithId: CreateInspectionStateWithId,
    val updateInspectionState: UpdateInspectionState,
    val deleteInspectionState: DeleteInspectionState,
    val getRepairStateList: GetRepairStateList,
    val createRepairState: CreateRepairState,
    val createRepairStateWithId: CreateRepairStateWithId,
    val updateRepairState: UpdateRepairState,
    val deleteRepairState: DeleteRepairState,
    val getTechnicianList: GetTechnicianList,
    val createTechnician: CreateTechnician,
    val createTechnicianWithId: CreateTechnicianWithId,
    val updateTechnician: UpdateTechnician,
    val deleteTechnician: DeleteTechnician,
    val getUser: GetUser,
    val getUserTypeList: GetUserTypeList,
    val createUserType: CreateUserType,
    val createUserTypeWithId: CreateUserTypeWithId,
    val updateUserType: UpdateUserType,
    val deleteUserType: DeleteUserType,
    val copyToClipboard: CopyToClipboard,
    val getDateFormattingTypes: GetDateFormattingTypes,
    val syncData: SyncData
)
