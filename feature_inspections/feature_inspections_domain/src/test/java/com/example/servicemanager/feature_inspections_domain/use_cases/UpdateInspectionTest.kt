package com.example.servicemanager.feature_inspections_domain.use_cases

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.test.inspection
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateInspectionTest {

    private lateinit var inspectionRepositoryFake: InspectionRepositoryFake
    private lateinit var updateInspection: UpdateInspection
    private lateinit var saveInspection: SaveInspection
    private lateinit var getInspection: GetInspection

    @BeforeEach
    fun setUp() {
        inspectionRepositoryFake = InspectionRepositoryFake()
        updateInspection = UpdateInspection(inspectionRepositoryFake)
        saveInspection = SaveInspection(inspectionRepositoryFake)
        getInspection = GetInspection(inspectionRepositoryFake)
    }

    @Test
    fun `UpdateInspection properly updates data`() = runBlocking {

        val inspection1 = inspection(
            inspectionId = "1",
            comment = "1")
        val inspection2 = inspection(
            inspectionId = "1",
            comment = "2"
        )

        saveInspection(inspection1).collect { }
        updateInspection(inspection2).collect { }

        val result = getInspection("1").first {
            it.resourceState == ResourceState.SUCCESS
        }
        val data: Inspection = result.data ?: inspection("0")

        assertThat(data.inspectionId).isEqualTo("1")
        assertThat(data.comment).isEqualTo("2")

    }
}