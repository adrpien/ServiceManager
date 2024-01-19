package com.example.servicemanager.feature_repairs_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_domain.R
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import javax.inject.Inject

class UpdateRepair @Inject constructor (
    private val repository: RepairRepository
) {

    suspend operator fun invoke(repair: Repair): Resource<String> {
        if (repair.openingDate > repair.closingDate) {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.closing_date_can_not_be_before_opening_date)
            )
        }
        if (repair.repairingDate > repair.closingDate) {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repairing_date_can_not_be_before_opening_date)
            )
        }
        return try {
            repository.updateRepair(repair)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repair_update_unknown_error)
            )
        }
    }
}