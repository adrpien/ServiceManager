package com.example.logger

import kotlin.reflect.full.declaredMemberProperties


object MapperExtensionFunction {
    fun <T : Any> T.toMap(): Map<String, Any?> {
        return this::class.declaredMemberProperties
            .associate { it.name to it.getter.call(this)}
    }


}