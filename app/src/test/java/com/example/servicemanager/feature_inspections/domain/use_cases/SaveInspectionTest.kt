package com.example.servicemanager.feature_inspections.domain.use_cases

import com.adrpien.tiemed.domain.use_case.inspections.SaveInspection
import com.example.servicemanager.feature_inspections.data.repository.FakeInspectionRepositoryImplementation
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test


class SaveInspectionTest {

    private lateinit var saveInspection: SaveInspection
    private lateinit var fakeInspectionRepository: FakeInspectionRepositoryImplementation

    @Before
    fun setUp() {
        fakeInspectionRepository = FakeInspectionRepositoryImplementation()
        saveInspection = SaveInspection(fakeInspectionRepository)

        val inspectionsToInsert = mutableListOf<Inspection>()
        ('a'..'z').forEachIndexed { index, char ->
            inspectionsToInsert.add(
                Inspection(
                    deviceIn = char.toString(),
                    deviceSn = char.toString()
                )
            )
        }
        inspectionsToInsert.shuffle()
        runBlocking {
            inspectionsToInsert.forEach{ fakeInspectionRepository.insertInspection(it) }
        }
    }

    @Test
    fun `insert with empty SN and IN TextField, returns error`() {


    }
}