package com.booking.app.utils

sealed class MyUiStates {
    object Loading : MyUiStates()
    object Success : MyUiStates()
    data class Error(val message: String) : MyUiStates()
    object NoConnection : MyUiStates()
}