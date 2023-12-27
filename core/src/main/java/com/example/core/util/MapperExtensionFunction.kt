package com.example.core.util

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KParameter
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties


object MapperExtensionFunction {
    fun <T : Any> T.toMap(): Map<String, Any?> {
        if(!this::class.isData) {
          throw IllegalArgumentException("dataClassObject must be data class")
        }
        return this::class.declaredMemberProperties
            .associate { it.name to it.getter.call(this)}
    }

    fun <T : Any> mapToDataClass(clazz: KClass<T>, properties: Map<String, Any>): T {
        val instance = clazz.createInstance()
        clazz.memberProperties.forEach { property ->
            if (property is KMutableProperty<*>) {
                val propertyName = property.name
                val propertyValue = properties[propertyName]
                if (propertyValue != null) {
                    property.setter.call(instance, propertyValue)
                }

            }
        }
        return instance
    }

    fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>) : T {
        val constructor = clazz.constructors.first()
        val args = constructor
            .parameters
            .map { it to map.get(it.name) }
            .toMap<KParameter, Any?>()
        return constructor.callBy(args)
    }
}