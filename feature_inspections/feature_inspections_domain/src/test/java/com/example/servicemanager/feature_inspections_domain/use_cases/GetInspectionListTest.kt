package com.example.servicemanager.feature_inspections_domain.use_cases

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactly
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.test.inspection
import com.example.test.inspectionFailed
import com.example.test.inspectionPassed
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderMonotonicity
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
import com.example.servicemanager.feature_inspections_domain.util.MainCoroutineExtension
import com.example.test.hospitalDluga
import com.example.test.hospitalORSK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

// @ExtendWith(MainCoroutineExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetInspectionListTest {

    private lateinit var inspectionRepositoryFake: InspectionRepository
    private lateinit var getInspectionList: GetInspectionList
    private lateinit var getInspection: GetInspection
    private lateinit var saveInspection: SaveInspection
    private lateinit var updateInspection: UpdateInspection


    companion object{
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }
    @BeforeEach
    fun setUp() {
        inspectionRepositoryFake = InspectionRepositoryFake()
        getInspection = GetInspection(inspectionRepositoryFake)
        getInspectionList = GetInspectionList(inspectionRepositoryFake)
        updateInspection = UpdateInspection(inspectionRepositoryFake)
        saveInspection = SaveInspection(inspectionRepositoryFake)
    }

/*
    @Test
    fun `inspectionOrderType argument properly order by hospital`() = runTest {
        val hospital3 = hospitalDluga()
        val hospital2 = hospitalORSK()
        val hospital1 = hospitalORSK()

        val inspection1 = inspection("1", hospitalId = hospital1.hospitalId)
        val inspection2 = inspection("2", hospitalId = hospital2.hospitalId)
        val inspection3 = inspection("3", hospitalId = hospital3.hospitalId)

        saveInspection(inspection1)
        saveInspection(inspection2)
        saveInspection(inspection3)
        advanceUntilIdle()

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
    fun `inspectionOrderType argument properly order by state`() = runTest {
        val inspection1 = inspectionFailed("1")
        val inspection2 = inspectionFailed("2")
        val inspection3 = inspectionPassed("3")

        saveInspection(inspection1)
        saveInspection(inspection2)
        saveInspection(inspection3)
        advanceUntilIdle()

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
    fun `inspectionOrderType argument properly order by date`() = runBlocking<Unit> {
        val inspection1 = inspection("1", inspectionDate = "1670799600000") // 2022/12/12
        val inspection2 = inspection("2", inspectionDate = "1697061600000") // 2023/10/12
        val inspection3 = inspection("3", inspectionDate = "1633989600000") // 2021/10/12

        saveInspection(inspection1)
        saveInspection(inspection2)
        saveInspection(inspection3)

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

        saveInspection(inspection1)
        saveInspection(inspection2)

        val result = getInspectionList(
            hospitalFilter = hospital1
        )
            .first {
                it.resourceState == ResourceState.SUCCESS
            }
        val data: List<Inspection> = result.data ?: emptyList()

        assertThat(data).contains(inspection1)
        assertThat(data).doesNotContain(inspection2)
    }*/

    @Test
    fun `searchQuery argument properly filters out inspectionList`() = runTest {

        val inspection1 = inspection("1")
        val inspection2 = inspection("2", deviceName = "Pompa infuzyjna")
        val inspection3 = inspection("3", deviceName = "pompa infuzyjna")
        val inspection4 = inspection("4")

        saveInspection(inspection1)
        saveInspection(inspection2)
        saveInspection(inspection3)
        saveInspection(inspection4)
        advanceUntilIdle()

        getInspectionList(
            searchQuery = "pompa",
            accessedHospitalIdList = listOf("")
        ).test() {
            val result = awaitItem().data ?: emptyList()
            assertThat(result).contains(inspection2)
            assertThat(result).contains(inspection2)
            awaitComplete()
        }
    }
}
