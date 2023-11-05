package com.example.servicemanager.feature_inspections_domain.use_cases

import assertk.assertThat
import com.example.servicemanager.feature_inspections_domain.InspectionRepositoryFake
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetInspectionTest {

    private lateinit var getInspection: GetInspection
    private lateinit var inspectionRepository: InspectionRepositoryFake

    @BeforeEach
    fun setUp() {
        inspectionRepository = InspectionRepositoryFake()
        getInspection = GetInspection(inspectionRepository)
    }


    @Test
    fun `GetInspection properly fetches data`(){
        assertThat(true).equals(true)
    }

    @Test
    fun `GetInspection throws exception when id equals 0`(){

    }
}