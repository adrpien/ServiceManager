package com.example.servicemanager.feature_app.data.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class FakeAppRepositoryImplementation(): AppRepository {



    override fun getSignature(signatureId: String): Flow<Resource<ByteArray>> {
        TODO("Not yet implemented")
    }

    override fun updateSignature(
        signatureId: String,
        byteArray: ByteArray,
    ): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun createSignature(
        signatureId: String,
        byteArray: ByteArray,
    ): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getHospitalList(): Flow<Resource<List<Hospital>>> {
        TODO("Not yet implemented")
    }

    override fun getTechnicianList(): Flow<Resource<List<Technician>>> {
        TODO("Not yet implemented")
    }

    override fun getEstStateList(): Flow<Resource<List<EstState>>> {
        TODO("Not yet implemented")
    }

    override fun getInspectionStateList(): Flow<Resource<List<InspectionState>>> {
        TODO("Not yet implemented")
    }

    override fun getRepairStateList(): Flow<Resource<List<RepairState>>> {
        TODO("Not yet implemented")
    }

}