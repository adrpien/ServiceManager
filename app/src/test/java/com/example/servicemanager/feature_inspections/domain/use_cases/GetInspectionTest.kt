package com.example.servicemanager.feature_inspections.domain.use_cases

import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.SaveInspection
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections.data.repository.FakeInspectionRepositoryImplementation
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetInspectionTest () {

    private lateinit var fakeInspectionRepository: FakeInspectionRepositoryImplementation
    private lateinit var getInspection: GetInspection
    private lateinit var saveInspection: SaveInspection

    @Before
    fun setUp() {
        fakeInspectionRepository = FakeInspectionRepositoryImplementation()
        getInspection = GetInspection(fakeInspectionRepository)
        saveInspection = SaveInspection(fakeInspectionRepository)
    }

    @Test
    fun `get inspection with empty empty inspectionid, return error`() = runBlocking {
        getInspection("").collect() { resource ->
            when(resource.resourceState) {
                ResourceState.ERROR -> {
                    assertThat(resource.resourceState).isEqualTo(ResourceState.ERROR)
                }
                ResourceState.SUCCESS -> Unit
                ResourceState.LOADING -> Unit
            }
        }
    }
    @Test
    fun `get inspection with correct inspectionid, return success`() = runBlocking {
        saveInspection(Inspection(inspectionId = "111"))
        getInspection("111").collect() { resource ->
            when(resource.resourceState) {
                ResourceState.ERROR -> {
                }
                ResourceState.SUCCESS -> {
                    assertThat(resource.resourceState).isEqualTo(ResourceState.SUCCESS)
                }

                ResourceState.LOADING -> Unit
            }
        }
    }

}