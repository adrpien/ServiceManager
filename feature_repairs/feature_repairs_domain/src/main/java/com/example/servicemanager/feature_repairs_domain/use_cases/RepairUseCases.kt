package com.example.servicemanager.feature_repairs_domain.use_cases

data class RepairUseCases(
    val updateRepair: UpdateRepair,
    val getRepair: GetRepair,
    val saveRepair: SaveRepair,
    val getRepairList: GetRepairList
)
