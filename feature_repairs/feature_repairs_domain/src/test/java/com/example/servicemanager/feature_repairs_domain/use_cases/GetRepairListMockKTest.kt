package com.example.servicemanager.feature_repairs_domain.use_cases

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.feature_repairs_domain.RepairRepositoryFake
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderMonotonicity
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType
import com.example.test.hospitalDluga
import com.example.test.hospitalORSK
import com.example.test.repairFinished
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate


/*
* I know that using mockK in this situation is really not required and optimal,
* but just to to train and show.
*/
class GetRepairListMockKTest {

    private lateinit var repairRepositoryMockK: RepairRepository
    private lateinit var getRepairList: GetRepairList
    private lateinit var saveRepair: SaveRepair
    private lateinit var updateRepair: UpdateRepair
    private lateinit var getRepair: GetRepair

    private val repair1 = repairFinished(
        repairId = "1",
        hospitalId = hospitalORSK().hospitalId,
        repairingDate = LocalDate.of(2022, 4, 8).toEpochDay().toString(),
    )
    private val repair2 = repairFinished(
        repairId = "2",
        hospitalId = hospitalDluga().hospitalId,
        repairingDate = LocalDate.of(2023, 9, 21).toEpochDay().toString(),
    )
    private val repair3 = repairFinished(
        repairId = "3",
        hospitalId = hospitalORSK().hospitalId,
        repairingDate = LocalDate.of(2020, 6, 1).toEpochDay().toString(),
    )
    private val repair4 = repairFinished(
        repairId = "4",
        hospitalId = hospitalDluga().hospitalId,
        repairingDate = LocalDate.of(2023, 2, 24).toEpochDay().toString(),
    )

    private val repairListTest: List<Repair> = mutableListOf(
        repair1,
        repair2,
        repair3,
        repair4
    )

    @BeforeEach
    fun setUp() {
        // repairRepositoryMockK = RepairRepositoryFake()
        repairRepositoryMockK = mockk(relaxed = true)
        getRepair = GetRepair(repairRepositoryMockK)
        saveRepair = SaveRepair(repairRepositoryMockK)
        updateRepair = UpdateRepair(repairRepositoryMockK)
        getRepairList = GetRepairList(repairRepositoryMockK)
    }

    @Test
    fun `repairOrderType argument properly order by date`() = runBlocking {
        coEvery {
            repairRepositoryMockK.getRepairList()
        } returns flow {
            emit(Resource(ResourceState.SUCCESS, repairListTest))
        }
        coEvery {
            repairRepositoryMockK.getRepairListFromLocal()
        } returns flow {
            emit(Resource(ResourceState.SUCCESS, repairListTest))
        }


        val result = getRepairList(
            repairOrderType = RepairOrderType.Date(RepairOrderMonotonicity.Ascending)
        ).first { it.resourceState == ResourceState.SUCCESS }

        val data: List<Repair> = result.data ?: emptyList()

        assertThat(data[0].repairId).isEqualTo("3")
        assertThat(data[1].repairId).isEqualTo("1")
        assertThat(data[2].repairId).isEqualTo("4")
        assertThat(data[3].repairId).isEqualTo("2")

    }
}