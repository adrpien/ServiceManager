package com.example.servicemanager.feature_inspections_domain.use_cases

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.test.test_data_generators.inspection
import com.example.test.test_data_generators.inspectionFailed
import com.example.test.test_data_generators.inspectionPassed
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderMonotonicity
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
import com.example.test.test_data_generators.hospitalDluga
import com.example.test.test_data_generators.hospitalORSK
import com.example.test.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
            inspectionOrderType = InspectionOrderType.Hospital(InspectionOrderMonotonicity.Ascending),
            accessedHospitalIdList = listOf(
                hospital1.hospitalId,
                hospital2.hospitalId,
                hospital3.hospitalId

            )

        ).test {
            val result = awaitItem().data ?: emptyList()
            assertThat(result[0]).isEqualTo(inspection3)
            awaitComplete()
        }
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
            inspectionOrderType = InspectionOrderType.State(InspectionOrderMonotonicity.Ascending),
            accessedHospitalIdList = listOf("")
        ).test {
            val emission1 = awaitItem()
            assertThat(emission1.data?.get(0)).isEqualTo(inspection3)
            awaitComplete()
        }

    }
    @Test
    fun `inspectionOrderType argument properly order by date`() = runTest {
        val inspection1 = inspection(
            "1",
            inspectionDate = "1670799600000"
        ) // 2022/12/12
        val inspection2 = inspection(
            "2",
            inspectionDate = "1697061600000"
        ) // 2023/10/12
        val inspection3 = inspection(
            "3",
            inspectionDate = "1633989600000"
        ) // 2021/10/12

        saveInspection(inspection1)
        saveInspection(inspection2)
        saveInspection(inspection3)
        advanceUntilIdle()

        val result = getInspectionList(
            inspectionOrderType = InspectionOrderType.Date(InspectionOrderMonotonicity.Ascending),
            accessedHospitalIdList = listOf("")
        ).test {
            val result = awaitItem().data ?: emptyList()
            assertThat(result[0]).isEqualTo(inspection3)
            awaitComplete()
        }
    }
    @Test
    fun `hospitalFilter argument properly filters out inspectionList`() = runTest {
        val hospital1 = hospitalDluga()
        val hospital2 = hospitalORSK()

        val inspection1 = inspection("1", hospitalId = hospital1.hospitalId)
        val inspection2 = inspection("2", hospitalId = hospital2.hospitalId)

        saveInspection(inspection1)
        saveInspection(inspection2)

        val result = getInspectionList(
            hospitalFilter = hospital1,
            accessedHospitalIdList = listOf(
                hospital1.hospitalId,
                hospital2.hospitalId
            )
        ).test {
            val result = awaitItem().data ?: emptyList()
            assertThat(result).contains(inspection1)
            assertThat(result).doesNotContain(inspection2)
            awaitComplete()
        }

    }
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
    @Test
    fun `accessedHospitalFilter argument properly filters out inspectionList`() = runTest {
        val hospital1 = hospitalDluga()
        val hospital2 = hospitalORSK()

        val inspection1 = inspection("1", hospitalId = hospital1.hospitalId)
        val inspection2 = inspection("2", hospitalId = hospital2.hospitalId)

        saveInspection(inspection1)
        saveInspection(inspection2)
        advanceUntilIdle()

        val result = getInspectionList(
            hospitalFilter = hospital1,
            accessedHospitalIdList = listOf(
                hospital1.hospitalId,
            )
        ).test {
            val result = awaitItem().data ?: emptyList()
            assertThat(result).contains(inspection1)
            assertThat(result).doesNotContain(inspection2)
            awaitComplete()
        }

    }
}
