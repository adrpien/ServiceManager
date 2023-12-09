package com.example.core.util


/*
    *************** OUT KEYWORD *******************
    * List<out T> in Kotlin is equivalent to List<? extends T> in Java.
    * List<in T> in Kotlin is equivalent to List<? super T> in Java
 */

// We can wrap any data, in order to have access to this data from activities and fragments
// Class for error and loading handling
// Example: Wrapped around list of songs
data class Resource <out T>(
    val resourceState: ResourceState,
    val data: T? = null,
    val message: UiText? = null
    ) {
    /*
    ********** COMPANION OBJECT *********************
    *
     */
    companion object {
        fun <T> success(data: T?) = Resource(ResourceState.SUCCESS, data, null)

        fun <T> error(message: UiText?, data: T?) = Resource(ResourceState.ERROR, data, message)

        fun <T> loading(data: T?) = Resource(ResourceState.LOADING, data, null)
    }

}

// Class which describes statuses our resource can have
// Status loading is to show progress bar, when our resource is not ready
enum class ResourceState {
    ERROR,
    SUCCESS ,
    LOADING
}