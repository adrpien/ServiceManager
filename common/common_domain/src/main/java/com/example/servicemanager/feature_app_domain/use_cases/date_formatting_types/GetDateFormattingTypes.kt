package com.example.servicemanager.feature_app_domain.use_cases.date_formatting_types

import com.example.core.util.DateFormattingType
import javax.inject.Inject
import kotlin.reflect.full.createInstance

class GetDateFormattingTypes @Inject constructor(
) {
    operator fun invoke(): List<DateFormattingType> {
        return DateFormattingType::class.sealedSubclasses.mapNotNull { subclass ->
            subclass.createInstance()
        }
    }
}