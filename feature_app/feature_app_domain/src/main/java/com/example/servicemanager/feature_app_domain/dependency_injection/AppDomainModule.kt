package com.example.servicemanager.feature_app_domain.dependency_injection

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_app_domain.use_cases.clipboard.CopyToClipboard
import com.example.servicemanager.feature_app_domain.use_cases.est_states.CreateEstState
import com.example.servicemanager.feature_app_domain.use_cases.est_states.DeleteEstState
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app_domain.use_cases.signatures.GetSignature
import com.example.servicemanager.feature_app_domain.use_cases.signatures.SaveSignature
import com.example.servicemanager.feature_app_domain.use_cases.signatures.UpdateSignature
import com.example.servicemanager.feature_app_domain.use_cases.est_states.GetEstStateList
import com.example.servicemanager.feature_app_domain.use_cases.est_states.UpdateEstState
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.CreateHospital
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.DeleteHospital
import com.example.servicemanager.feature_app_domain.use_cases.hospitals.UpdateHospital
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.CreateInspectionState
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.DeleteInspectionState
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.GetInspectionStateList
import com.example.servicemanager.feature_app_domain.use_cases.inspection_states.UpdateInspectionState
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.CreateRepairState
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.DeleteRepairState
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.GetRepairStateList
import com.example.servicemanager.feature_app_domain.use_cases.repair_states.UpdateRepairState
import com.example.servicemanager.feature_app_domain.use_cases.technicians.CreateTechnician
import com.example.servicemanager.feature_app_domain.use_cases.technicians.DeleteTechnician
import com.example.servicemanager.feature_app_domain.use_cases.technicians.GetTechnicianList
import com.example.servicemanager.feature_app_domain.use_cases.technicians.UpdateTechnician
import com.example.servicemanager.feature_app_domain.use_cases.user_types.CreateUserType
import com.example.servicemanager.feature_app_domain.use_cases.user_types.DeleteUserType
import com.example.servicemanager.feature_app_domain.use_cases.user_types.GetUserTypeList
import com.example.servicemanager.feature_app_domain.use_cases.user_types.UpdateUserType
import com.example.servicemanager.feature_app_domain.use_cases.users.GetUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDomainModule {

    @Provides
    @Singleton
    fun provideClipboardManager(app: Application): ClipboardManager {
        return app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }
    @Provides
    @Singleton
    fun provideAppUseCases(repository: AppRepository, clipboardManager: ClipboardManager): AppUseCases {
        return AppUseCases(
            getHospitalList = GetHospitalList(repository),
            createHospital = CreateHospital(repository),
            updateHospital = UpdateHospital(repository),
            deleteHospital = DeleteHospital(repository),
            saveSignature = SaveSignature(repository),
            getSignature = GetSignature(repository),
            updateSignature = UpdateSignature(repository),
            getEstStateList = GetEstStateList(repository),
            createEstState = CreateEstState(repository),
            updateEstState = UpdateEstState(repository),
            deleteEstState = DeleteEstState(repository),
            getInspectionStateList = GetInspectionStateList(repository),
            createInspectionState = CreateInspectionState(repository),
            updateInspectionState = UpdateInspectionState(repository),
            deleteInspectionState = DeleteInspectionState(repository),
            getRepairStateList = GetRepairStateList(repository),
            createRepairState = CreateRepairState(repository),
            updateRepairState = UpdateRepairState(repository),
            deleteRepairState = DeleteRepairState(repository),
            getTechnicianList = GetTechnicianList(repository),
            createTechnician = CreateTechnician(repository),
            updateTechnician = UpdateTechnician(repository),
            deleteTechnician = DeleteTechnician(repository),
            getUser = GetUser(repository),
            getUserTypeList = GetUserTypeList(repository),
            createUserType = CreateUserType(repository),
            updateUserType = UpdateUserType(repository),
            deleteUserType = DeleteUserType(repository),
            copyToClipboard = CopyToClipboard(clipboardManager)
        )
    }

}

