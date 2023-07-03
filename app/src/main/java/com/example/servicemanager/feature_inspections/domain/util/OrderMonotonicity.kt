package com.adrpien.noteapp.feature_notes.domain.util

sealed class OrderMonotonicity(){
    object Descending: OrderMonotonicity()
    object Ascending: OrderMonotonicity()
}
