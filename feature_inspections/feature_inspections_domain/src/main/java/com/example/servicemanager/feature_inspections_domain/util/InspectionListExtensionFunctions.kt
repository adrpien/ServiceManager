package com.example.servicemanager.feature_inspections.domain.util

import com.example.servicemanager.feature_inspections_domain.model.Inspection

class InspectionListExtensionFunctions() {

    companion object {

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