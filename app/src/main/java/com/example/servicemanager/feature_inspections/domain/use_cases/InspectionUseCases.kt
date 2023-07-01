package com.example.servicemanager.feature_inspections.domain.use_cases

import com.adrpien.tiemed.domain.use_case.inspections.CreateInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.adrpien.tiemed.domain.use_case.inspections.UpdateInspection

data class InspectionUseCases(
    val createInspection: CreateInspection,
    val getInspection: GetInspection,
    val getInspectionList: GetInspectionList,
    val updateInspection: UpdateInspection
)
