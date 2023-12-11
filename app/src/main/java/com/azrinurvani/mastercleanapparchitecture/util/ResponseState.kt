package com.azrinurvani.mastercleanapparchitecture.util

sealed class ResponseState<T>(val data : T? = null, val message: String ? = null){
    class Loading<T>(data: T?=null) : ResponseState<T>(data)
    class Success<T>(data: T? = null) : ResponseState<T>(data)
    class Error<T>(message: String?=null,data: T? = null) : ResponseState<T>(data,message)
}
