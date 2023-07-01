package com.example.servicemanager.feature_app.domain.use_cases

import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.example.servicemanager.feature_app.domain.model.Device
import com.example.servicemanager.feature_app.domain.model.EstState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_app.domain.model.Technician
import com.example.servicemanager.feature_app.domain.use_cases.devices.CreateDevice
import com.example.servicemanager.feature_app.domain.use_cases.devices.GetDevice
import com.example.servicemanager.feature_app.domain.use_cases.devices.GetDeviceList
import com.example.servicemanager.feature_app.domain.use_cases.devices.UpdateDevice
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.signatures.CreateSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.GetSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.UpdateSignature
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetRepairStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
import com.example.servicemanager.feature_inspections.domain.model.Inspection

data class AppUseCases(
    val createDevice: CreateDevice,
    val getDevice: GetDevice,
    val getDeviceList: GetDeviceList,
    val updateDevice: UpdateDevice,
    val getHospitalList: GetHospitalList,
    val createSignature: CreateSignature,
    val getSignature: GetSignature,
    val updateSignature: UpdateSignature,
    val getEstStateList: GetEstStateList,
    val getInspectionStateList: GetInspectionStateList,
    val getRepairStateList: GetRepairStateList,
    val getTechnicianList: GetTechnicianList,
    )
