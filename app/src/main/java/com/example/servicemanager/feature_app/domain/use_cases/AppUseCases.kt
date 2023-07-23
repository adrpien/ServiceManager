package com.example.servicemanager.feature_app.domain.use_cases

import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.signatures.SaveSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.GetSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.UpdateSignature
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetRepairStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList

data class AppUseCases(
    val saveSignature: SaveSignature,
    val getSignature: GetSignature,
    val updateSignature: UpdateSignature,
    val getHospitalList: GetHospitalList,
    val getEstStateList: GetEstStateList,
    val getInspectionStateList: GetInspectionStateList,
    val getRepairStateList: GetRepairStateList,
    val getTechnicianList: GetTechnicianList,
    )
