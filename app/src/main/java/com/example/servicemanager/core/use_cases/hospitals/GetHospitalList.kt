package com.adrpien.tiemed.domain.use_case.hospitals

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.Hospital
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHospitalList @Inject constructor (
    private val repository: TiemedRepository
) {

    operator fun invoke(): Flow<Resource<List<Hospital>>> {
        return repository.getHospitalList()
    }

}