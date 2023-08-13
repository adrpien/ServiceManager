package com.example.servicemanager.feature_inspections.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
class InspectionDatabaseDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("inspection_test_db")
    lateinit var inspectionDatabase: InspectionDatabase
    private lateinit var inspectionDatabaseDao: InspectionDatabaseDao

    @Before
    fun setup() {
         hiltRule.inject()
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

    @Test
    fun deleteInspection() = runTest {
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
        inspectionDatabaseDao.deleteInspection(inspectionEntity.inspectionId)
        val result = inspectionDatabaseDao.getInspectionList()
        assertThat(result).doesNotContain(inspectionEntity)
    }

    @Test
    fun getInspectionList() = runTest {
        val inspectionEntity1 = InspectionEntity(
            inspectionId = "test1",
        )
        val inspectionEntity2 = InspectionEntity(
            inspectionId = "test2",
        )
        val inspectionEntity3 = InspectionEntity(
            inspectionId = "test3",
        )

        inspectionDatabaseDao.insertInspection(inspectionEntity1)
        inspectionDatabaseDao.insertInspection(inspectionEntity2)
        inspectionDatabaseDao.insertInspection(inspectionEntity3)
        val result = inspectionDatabaseDao.getInspectionList()
        assertThat(result).containsExactly(
            inspectionEntity1,
            inspectionEntity2,
            inspectionEntity3
        )
    }

    @Test
    fun deleteAllInspections() = runTest {
        val inspectionEntity1 = InspectionEntity(
            inspectionId = "test1",
        )
        val inspectionEntity2 = InspectionEntity(
            inspectionId = "test2",
        )
        val inspectionEntity3 = InspectionEntity(
            inspectionId = "test3",
        )
        inspectionDatabaseDao.insertInspection(inspectionEntity1)
        inspectionDatabaseDao.insertInspection(inspectionEntity2)
        inspectionDatabaseDao.insertInspection(inspectionEntity3)
        inspectionDatabaseDao.deleteAllInspections()
        val result = inspectionDatabaseDao.getInspectionList()
        assertThat(result).isEmpty()
    }
}