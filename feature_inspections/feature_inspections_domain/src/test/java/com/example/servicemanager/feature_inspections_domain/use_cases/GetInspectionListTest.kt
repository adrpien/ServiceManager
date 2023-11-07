package com.example.servicemanager.feature_inspections_domain.use_cases

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactly
import assertk.assertions.doesNotContain
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNullOrEmpty
import assertk.assertions.isTrue
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderMonotonicity
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
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
    fun `inspectionOrderType argument properly order by hospital`() = runBlocking {
        val hospital3 = hospitalDluga()
        val hospital2 = hospitalORSK()
        val hospital1 = hospitalORSK()

        val inspection1 = inspection("1", hospitalId = hospital1.hospitalId)
        val inspection2 = inspection("2", hospitalId = hospital2.hospitalId)
        val inspection3 = inspection("3", hospitalId = hospital3.hospitalId)

        saveInspection(inspection1).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection2).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection3).first { it.resourceState == ResourceState.SUCCESS }

        val result = getInspectionList(
            inspectionOrderType = InspectionOrderType.Hospital(InspectionOrderMonotonicity.Ascending)
        )
            .first {
                it.resourceState == ResourceState.SUCCESS
            }
        val data: List<Inspection> = result.data ?: emptyList()

        assertThat(inspection3).isEqualTo(data[0])
    }
    @Test
    fun `inspectionOrderType argument properly order by state`() = runBlocking {
        val inspection1 = inspectionFailed("1")
        val inspection2 = inspectionFailed("2")
        val inspection3 = inspectionPassed("3")

        saveInspection(inspection1).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection2).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection3).first { it.resourceState == ResourceState.SUCCESS }

        val result = getInspectionList(
            inspectionOrderType = InspectionOrderType.State(InspectionOrderMonotonicity.Ascending)
        )
            .first {
                it.resourceState == ResourceState.SUCCESS
            }
        val data: List<Inspection> = result.data ?: emptyList()

        assertThat(inspection3).isEqualTo(data[0])
    }

    @Test
    fun `inspectionOrderType argument properly order by date`() = runBlocking {
        val inspection1 = inspection("1", inspectionDate = "1670799600000") // 2022/12/12
        val inspection2 = inspection("2", inspectionDate = "1697061600000") // 2023/10/12
        val inspection3 = inspection("3", inspectionDate = "1633989600000") // 2021/10/12

        saveInspection(inspection1).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection2).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection3).first { it.resourceState == ResourceState.SUCCESS }

        val result = getInspectionList(
            inspectionOrderType = InspectionOrderType.Date(InspectionOrderMonotonicity.Ascending)
        )
            .first {
                it.resourceState == ResourceState.SUCCESS
            }
        val data: List<Inspection> = result.data ?: emptyList()

        assertThat(inspection3).isEqualTo(data[0])
        assertThat(inspection1).isEqualTo(data[1])
        assertThat(inspection2).isEqualTo(data[2])
    }

    @Test
    fun `hospitalFilter argument properly filters out inspectionList`() = runBlocking {
        val hospital1 = hospitalDluga()
        val hospital2 = hospitalORSK()

        val inspection1 = inspection("1", hospitalId = hospital1.hospitalId)
        val inspection2 = inspection("2", hospitalId = hospital2.hospitalId)

        saveInspection(inspection1).first { it.resourceState == ResourceState.SUCCESS }
        saveInspection(inspection2).first { it.resourceState == ResourceState.SUCCESS }

        val result = getInspectionList(
            hospitalFilter = hospital1
        )
            .first {
                it.resourceState == ResourceState.SUCCESS
            }
        val data: List<Inspection> = result.data ?: emptyList()

        assertThat(data).contains(inspection1)
        assertThat(data).doesNotContain(inspection2)
    }

    @Test
    fun `searchQuery argument properly filters out inspectionList`() = runBlocking {

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
