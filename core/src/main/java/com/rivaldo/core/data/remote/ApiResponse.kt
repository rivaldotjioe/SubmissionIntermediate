package com.rivaldo.core.data.remote

sealed class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : com.rivaldo.core.data.remote.ApiResponse<T>(data)
    class Loading<T>(data: T? = null) : com.rivaldo.core.data.remote.ApiResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : com.rivaldo.core.data.remote.ApiResponse<T>(data, message)
}