package com.example.servicemanager.common_domain.util

sealed class CacheOperationType(val operationName: String) {
    companion object {
        fun getCacheOperationType(string: String): CacheOperationType {
            when(string) {
                Update().operationName -> {
                    return Update()
                }
                Insert().operationName -> {
                    return Insert()
                }
            }
        return Insert()
        }
    }

    data class Update(val name: String = "UPDATE"): CacheOperationType(name)
    data class Insert(val name: String = "INSERT"): CacheOperationType(name)


}
