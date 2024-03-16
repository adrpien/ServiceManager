package com.example.servicemanager.feature_inspections_domain.use_cases

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
class SaveInspectionTest {

    private lateinit var  inspectionRepositoryFake: InspectionRepositoryFake
    private lateinit var saveInspection: SaveInspection
    private lateinit var getInspection: GetInspection

    companion object{
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {
        inspectionRepositoryFake = InspectionRepositoryFake()
        saveInspection = SaveInspection(inspectionRepositoryFake)
        getInspection = GetInspection(inspectionRepositoryFake)
    }

    @Test
    fun `saveInspection return ERROR when deviceSn and deviceIn are empty strings`() = runTest {
        val inspection = inspection(
            inspectionId = "1",
            deviceIn = "",
            deviceSn = "",
        )

        val resultState = saveInspection(inspection).resourceState
        advanceUntilIdle()
        assertThat(resultState).isEqualTo(ResourceState.ERROR)
    }

    /*
    inspectionDate is parsed to Long later and should contains only digits
     */
    @Test
    fun `saveInspection returns ERROR when inspections date contains not digits`() = runTest {
        val inspection = inspection(
            inspectionId = "1",
            inspectionDate = "1R32455"
        )

        val resultState = saveInspection(inspection).resourceState
        advanceUntilIdle()
        assertThat(resultState).isEqualTo(ResourceState.ERROR)
    }
}