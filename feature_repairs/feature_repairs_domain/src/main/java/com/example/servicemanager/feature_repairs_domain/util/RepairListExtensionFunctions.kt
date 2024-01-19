package com.example.servicemanager.feature_repairs_domain.util

import com.example.servicemanager.feature_repairs_domain.model.Repair

class RepairListExtensionFunctions {

    companion object {

        fun List<Repair>.orderRepairList(repairOrderType: RepairOrderType): List<Repair> {
            when (repairOrderType.orderMonotonicity) {
                is RepairOrderMonotonicity.Ascending -> {
                    when (repairOrderType) {
                        is RepairOrderType.Date -> {
                            return this.sortedBy { it.repairingDate }
                        }
                        is RepairOrderType.Hospital -> {
                            return this.sortedBy { it.hospitalId }
                        }
                        is RepairOrderType.State -> {
                            return this.sortedBy { it.repairStateId }
                        }
                    }

                }
                is RepairOrderMonotonicity.Descending -> {
                    when (repairOrderType) {
                        is RepairOrderType.Date -> {
                            return this.sortedByDescending { it.repairingDate }
                        }
                        is RepairOrderType.Hospital -> {
                            return this.sortedByDescending { it.hospitalId }
                        }
                        is RepairOrderType.State -> {
                            return this.sortedByDescending { it.repairStateId }
                        }
                    }
                }
            }

        }
    }
}