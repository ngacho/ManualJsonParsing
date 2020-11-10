package com.example.manualjsonparsing.util

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val errorMessage: String? = null
) {
    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(errorMessage: String?, data: T? =null): Resource<T> {
            return Resource(Status.ERROR, data, errorMessage)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data)
        }
    }

}