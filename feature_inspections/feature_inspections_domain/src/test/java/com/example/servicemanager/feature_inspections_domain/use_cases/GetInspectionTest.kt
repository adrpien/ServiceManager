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

class GetInspectionTest {

    private lateinit var getInspection: GetInspection
    private lateinit var inspectionRepository: InspectionRepositoryFake
    private lateinit var saveInspection: SaveInspection

    @BeforeEach
    fun setUp() {
        inspectionRepository = InspectionRepositoryFake()
        saveInspection = SaveInspection(inspectionRepository)
        getInspection = GetInspection(inspectionRepository)
    }


    @Test
    fun `GetInspection properly fetches valid data`() = runBlocking {
        val inspection3 = inspection("3")
        saveInspection(inspection3).first { it.resourceState == ResourceState.SUCCESS }
        val result = getInspection("3").first {
            it.resourceState == ResourceState.SUCCESS
        }
        val data: Inspection = result.data ?: inspection("0")

        assertThat(data.inspectionId).isEqualTo("3")
    }

    @Test
    fun `GetInspection return ERROR ResourceState when id is empty`() = runBlocking {
        val inspection = inspection("")
        saveInspection(inspection).first { it.resourceState == ResourceState.SUCCESS }
        val result = getInspection("").first {
            it.resourceState == ResourceState.ERROR
        }

        assertThat(result.resourceState).isEqualTo(ResourceState.ERROR)
    }
}