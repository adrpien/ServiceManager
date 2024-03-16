package com.example.servicemanager.feature_inspections_domain.use_cases

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import com.example.test.test_data_generators.inspection
import com.example.test.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class GetInspectionTest {

    private lateinit var getInspection: GetInspection
    private lateinit var inspectionRepository: InspectionRepositoryFake
    private lateinit var saveInspection: SaveInspection

    companion object{
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }


    @BeforeEach
    fun setUp() {
        inspectionRepository = InspectionRepositoryFake()
        saveInspection = SaveInspection(inspectionRepository)
        getInspection = GetInspection(inspectionRepository)
    }


    @Test
    fun `GetInspection properly fetches existing data`() = runTest {
        val inspection3 = inspection("3")
        saveInspection(inspection3)
        advanceUntilIdle()
        val result = getInspection(inspection3.inspectionId).test {
            val emission1 = awaitItem()
            val emission2 = awaitItem()
            assertThat(emission1.data?.inspectionId).isEqualTo(inspection3.inspectionId)
            assertThat(emission1.resourceState).isEqualTo(ResourceState.LOADING)
            assertThat(emission2.resourceState).isEqualTo(ResourceState.SUCCESS)
            awaitComplete()
        }

    }

}