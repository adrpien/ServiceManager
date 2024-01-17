package com.example.servicemanager.feature_app_domain.util

sealed class CacheOperationType(val operationName: String) {
    companion object {
        fun getCacheOperationType(string: String): CacheOperationType {
            when(string) {
                CacheOperationType.Update().operationName -> {
                    return CacheOperationType.Update()
                }
                CacheOperationType.Insert().operationName -> {
                    return CacheOperationType.Insert()
                }
            }
        return CacheOperationType.Insert()
        }
    }

    data class Update(val name: String = "UPDATE"): CacheOperationType(name)
    data class Insert(val name: String = "INSERT"): CacheOperationType(name)


}
