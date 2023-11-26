package com.example.servicemanager.feature_repairs_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.logger_data.logger.FirebaseLogger
import com.example.logger_domain.logger.AppLogger
import com.example.servicemanager.feature_repairs_data.local.RepairDatabase
import com.example.servicemanager.feature_repairs_data.remote.RepairFirebaseApi
import com.example.servicemanager.feature_repairs_data.repository.RepairRepositoryImplementation
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import com.example.servicemanager.feature_repairs_domain.use_cases.GetRepair
import com.example.servicemanager.feature_repairs_domain.use_cases.GetRepairList
import com.example.servicemanager.feature_repairs_domain.use_cases.RepairUseCases
import com.example.servicemanager.feature_repairs_domain.use_cases.SaveRepair
import com.example.servicemanager.feature_repairs_domain.use_cases.UpdateRepair
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepairDataModule {


    @Provides
    @Singleton
    fun provideRepairFirebaseApi(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): RepairFirebaseApi {
        return RepairFirebaseApi(
            firebaseFirestore = firebaseFirestore,
            firebaseStorage = firebaseStorage
        )
    }

    @Provides
    @Singleton
    fun provideRepairDatabase(app: Application): RepairDatabase {
        return Room.databaseBuilder(
            app,
            RepairDatabase::class.java,
            "repair_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepairRepository(
        repairDatabase: RepairDatabase,
        repairFirebaseApi: RepairFirebaseApi,
        appLogger: AppLogger
    ): RepairRepository {
        return RepairRepositoryImplementation(
            repairDatabaseDao = repairDatabase.repairDatabaseDao,
            repairFirebaseApi = repairFirebaseApi,
            appLogger = appLogger
        )
    }
}


