package com.example.servicemanager.feature_inspections.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class InspectionDatabaseDaoTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var inspectionDatabase: InspectionDatabase
    private lateinit var inspectionDatabaseDao: InspectionDatabaseDao

    @Before
    fun setup() {
         inspectionDatabase = Room.inMemoryDatabaseBuilder(
             ApplicationProvider.getApplicationContext(),
             InspectionDatabase::class.java,
         ).allowMainThreadQueries().build()
        inspectionDatabaseDao = inspectionDatabase.inspectionDatabaseDao
    }

    @After
    fun tearDown() {
        inspectionDatabase.close()
    }

    @Test
    fun insertInspection() = runTest {
        val inspectionEntity = InspectionEntity(
            inspectionId = "test",
            hospitalId = "ExF6PByjkDrHSCTzvYz3",
            ward = "AIT",
            comment = "comment",
            inspectionDate = "1683410400000",
            recipient = "Kamila Wodnicka",
            technicianId = "Hy0eROd4a8080VIk3wjy",
            inspectionStateId = "69h8l32O9qGKIRud9eiD",
            estStateId = "0jUOtDLuw8JUjgK5YpFW",
            deviceName = "Pompa infuzyjna",
            deviceManufacturer = "Ascor",
            deviceModel = "AP14",
            deviceSn = "2014/198/776",
            deviceIn = "T-802/1976"
        )
        inspectionDatabaseDao.insertInspection(inspectionEntity)
        val result = inspectionDatabaseDao.getInspectionList()
        assertThat(result).contains(inspectionEntity)
    }

}