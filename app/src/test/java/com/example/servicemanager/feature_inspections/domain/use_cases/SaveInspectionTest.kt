package com.example.servicemanager.feature_inspections.domain.use_cases

import com.adrpien.tiemed.domain.use_case.inspections.SaveInspection
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_inspections.data.repository.FakeInspectionRepositoryImplementation
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test


class SaveInspectionTest {

    private lateinit var saveInspection: SaveInspection
    private lateinit var fakeInspectionRepository: FakeInspectionRepositoryImplementation

    @Before
    fun setUp() {
        fakeInspectionRepository = FakeInspectionRepositoryImplementation()
        saveInspection = SaveInspection(fakeInspectionRepository)
    }

    @Test
    fun `insert with empty sn and in textfield, returns error`() = runBlocking {
        saveInspection(Inspection(deviceSn = "", deviceIn = "")).collect() { resource ->

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
    fun `insert correct inspection, returns success`() = runBlocking {
        saveInspection(Inspection(deviceSn = "2014/1987-876", deviceIn = "T-802/1986")).collect() { resource ->
            when(resource.resourceState) {
                ResourceState.SUCCESS -> {
                    assertThat(resource.resourceState).isEqualTo(ResourceState.SUCCESS)
                }
                ResourceState.ERROR -> Unit
                ResourceState.LOADING -> Unit
            }
        }
    }

}