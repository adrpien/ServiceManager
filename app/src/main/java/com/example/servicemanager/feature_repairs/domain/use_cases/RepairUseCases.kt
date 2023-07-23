package com.example.servicemanager.feature_repairs.domain.use_cases

import com.adrpien.tiemed.domain.use_case.repairs.GetRepair
import com.adrpien.tiemed.domain.use_case.repairs.GetRepairList
import com.adrpien.tiemed.domain.use_case.repairs.SaveRepair
import com.adrpien.tiemed.domain.use_case.repairs.UpdateRepair

data class RepairUseCases(
    val updateRepair: UpdateRepair,
    val getRepair: GetRepair,
    val saveRepair: SaveRepair,
    val getRepairList: GetRepairList
)
