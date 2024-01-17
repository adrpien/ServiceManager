package com.example.servicemanager.feature_repairs_domain.use_cases


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_domain.R
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import javax.inject.Inject

class SaveRepair @Inject constructor (
    private val repository: RepairRepository
) {

    // TODO opening date should not be bigger tha repairing date and returning date should be bigger than repairing date
    suspend operator fun invoke(repair: Repair): Resource<String> {
        return try {
            if (repair.deviceSn != "" && repair.deviceIn != "") {
                repository.insertRepair(repair)
            } else {
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.textfields_device_sn_and_device_in_are_empty)
                )
            }
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }
}