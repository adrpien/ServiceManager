package com.example.servicemanager.feature_inspections_domain.util

import androidx.core.text.isDigitsOnly
import com.example.servicemanager.feature_inspections_domain.model.Inspection

class InspectionListExtensionFunctions {

    companion object {

        fun List<Inspection>.verifyInspectionList(): Boolean {
            this.forEachIndexed() { index, inspection ->
                // verifying conditions
                if (inspection.deviceSn.isEmpty() && inspection.deviceIn.isEmpty()) return false
                if (!inspection.inspectionDate.isDigitsOnly()) return false

            }
            return true
        }

        fun List<Inspection>.orderInspectionList(inspectionOrderType: InspectionOrderType): List<Inspection> {
            when (inspectionOrderType.orderMonotonicity) {
                is InspectionOrderMonotonicity.Ascending -> {
                    when (inspectionOrderType) {
                        is InspectionOrderType.Date -> {
                            return this.sortedBy { it.inspectionDate }
                        }
                        is InspectionOrderType.Hospital -> {
                            return this.sortedBy { it.hospitalId }
                        }
                        is InspectionOrderType.State -> {
                            return this.sortedBy { it.inspectionStateId }
                        }
                    }

                }
                is InspectionOrderMonotonicity.Descending -> {
                    when (inspectionOrderType) {
                        is InspectionOrderType.Date -> {
                            return this.sortedByDescending { it.inspectionDate }
                        }
                        is InspectionOrderType.Hospital -> {
                            return this.sortedByDescending { it.hospitalId }
                        }
                        is InspectionOrderType.State -> {
                            return this.sortedByDescending { it.inspectionStateId }
                        }
                    }
                }
            }

        }
    }
}