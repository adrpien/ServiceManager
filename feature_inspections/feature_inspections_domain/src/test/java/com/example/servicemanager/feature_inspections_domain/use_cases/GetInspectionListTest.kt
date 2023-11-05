package com.example.servicemanager.feature_inspections_domain.use_cases

import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isTrue
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.servicemanager.feature_inspections_domain.model.Inspection
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
        getInspection = GetInspection(InspectionRepositoryFake())
        getInspectionList = GetInspectionList(InspectionRepositoryFake())
        updateInspection = UpdateInspection(InspectionRepositoryFake())
        saveInspection = SaveInspection(InspectionRepositoryFake())
        inspectionRepositoryFake = InspectionRepositoryFake()
    }


    // TODO rememeber to check if the fun is flaky, make parametrized test
    @Test
    fun `searchQuery properly filters out inspectionList`() = runBlocking<Unit> {

        val inspection1 = inspection("1")
        val inspection2 = inspection("2", deviceName = "Pompa infuzyjna")
        val inspection3 = inspection("3", deviceName = "pompa infuzyjna")
        val inspection4 = inspection("4")

        saveInspection(inspection1)
        saveInspection(inspection2)
        saveInspection(inspection3)
        saveInspection(inspection4)

        val result = getInspectionList()
            .first {
                it.data?.isNotEmpty() ?: false
            }
        val data: List<Inspection> = result.data ?: emptyList()

        assertk.assertThat(data).contains(inspection2)
        assertk.assertThat(data).contains(inspection3)
        assertk.assertThat(data).doesNotContain(inspection1)
    }

    @Test
    fun `test`() {
        assertk.assertThat(true).isTrue()
    }
}
