package com.example.servicemanager.common_domain.model


data class EstState (
    val estStateId: String = "",
    val estState: String = ""
){

    override fun toString(): String {
        return this.estState
    }
}

