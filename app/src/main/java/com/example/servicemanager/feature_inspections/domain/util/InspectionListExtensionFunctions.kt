package com.example.servicemanager.feature_inspections.domain.util

import com.adrpien.noteapp.feature_notes.domain.util.OrderMonotonicity
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.example.servicemanager.feature_inspections.domain.model.Inspection

class InspectionListExtensionFunctions() {

    companion object {

        fun List<Inspection>.orderInspectionList(orderType: OrderType): List<Inspection> {
            when (orderType.orderMonotonicity) {
                is OrderMonotonicity.Ascending -> {
                    when (orderType) {
                        is OrderType.Date -> {
                            return this.sortedBy { it.inspectionDate }
                        }
                        is OrderType.Hospital -> {
                            return this.sortedBy { it.hospitalId }
                        }
                        is OrderType.State -> {
                            return this.sortedBy { it.inspectionStateId }
                        }
                    }

                }
                is OrderMonotonicity.Descending -> {
                    when (orderType) {
                        is OrderType.Date -> {
                            return this.sortedByDescending { it.inspectionDate }
                        }
                        is OrderType.Hospital -> {
                            return this.sortedByDescending { it.hospitalId }
                        }
                        is OrderType.State -> {
                            return this.sortedByDescending { it.inspectionStateId }
                        }
                    }
                }
            }

        }
    }
}