package com.example.logger

import kotlin.reflect.full.declaredMemberProperties


object MapperExtensionFunction {
    fun <T : Any> T.toMap(): Map<String, Any?> {
        if(!this::class.isData) {
          throw IllegalArgumentException("dataClassObject must be data class")
        }
        return this::class.declaredMemberProperties
            .associate { it.name to it.getter.call(this)}
    }


}