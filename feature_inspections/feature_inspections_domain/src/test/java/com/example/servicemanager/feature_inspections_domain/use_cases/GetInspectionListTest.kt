package com.example.servicemanager.feature_inspections_domain.use_cases

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEmpty
import assertk.assertions.isNullOrEmpty
import assertk.assertions.isTrue
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetInspectionListTest {

    private lateinit var inspectionRepositoryFake: InspectionRepositoryFake
    private lateinit var getInspectionList: GetInspectionList
    private lateinit var getInspection: GetInspection
    private lateinit var saveInspection: SaveInspection
    private lateinit var updateInspection: UpdateInspection

    @BeforeEach
    fun setUp() {
        inspectionRepositoryFake = InspectionRepositoryFake()
        getInspection = GetInspection(inspectionRepositoryFake)
        getInspectionList = GetInspectionList(inspectionRepositoryFake)
        updateInspection = UpdateInspection(inspectionRepositoryFake)
        saveInspection = SaveInspection(inspectionRepositoryFake)
    }


    @Test
    fun `searchQuery properly filters out inspectionList`() = runBlocking<Unit> {

        val inspection1 = inspection("1")
        val inspection2 = inspection("2", deviceName = "Pompa infuzyjna")
        val inspection3 = inspection("3", deviceName = "pompa infuzyjna")
        val inspection4 = inspection("4")

        saveInspection(inspection1).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection2).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection3).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection4).first { it.resourceState == ResourceState.SUCCESS }

        val result = getInspectionList(
            searchQuery = "pompa"
        )
       .first {
           it.resourceState == ResourceState.SUCCESS
       }
        val data: List<Inspection> = result.data ?: emptyList()

        assertk.assertThat(data).contains(inspection2)
        assertk.assertThat(data).contains(inspection3)
        assertk.assertThat(data).doesNotContain(inspection1)
        assertk.assertThat(data).doesNotContain(inspection4)
    }
}
