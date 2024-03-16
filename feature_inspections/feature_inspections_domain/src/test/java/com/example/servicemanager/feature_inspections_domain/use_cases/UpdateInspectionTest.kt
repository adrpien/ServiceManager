package com.example.servicemanager.feature_inspections_domain.use_cases

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
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
class UpdateInspectionTest {

    private lateinit var inspectionRepositoryFake: InspectionRepositoryFake
    private lateinit var updateInspection: UpdateInspection
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
        updateInspection = UpdateInspection(inspectionRepositoryFake)
        saveInspection = SaveInspection(inspectionRepositoryFake)
        getInspection = GetInspection(inspectionRepositoryFake)
    }

    @Test
    fun `UpdateInspection properly updates data`() = runTest {

        val inspection = inspection(
            inspectionId = "1",
            comment = "saved_comment"
        )
        val updatedInspection = inspection.copy(comment = "updated_comment")
        saveInspection(inspection)
        updateInspection(updatedInspection)

        val result = getInspection("1").test {
            val emission1 = awaitItem()
            val emission2 = awaitItem()
            assertThat(emission2.data?.comment).isEqualTo("updated_comment")
            awaitComplete()
        }
    }
}