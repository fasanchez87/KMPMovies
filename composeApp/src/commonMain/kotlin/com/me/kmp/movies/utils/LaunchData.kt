package com.me.kmp.movies.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

fun <T> ViewModel.launchFlow(
    stateFlow: MutableStateFlow<ResultObject<T>>,
    block: suspend () -> Flow<T?>
) {
    viewModelScope.launch {
        try {
            // stateFlow.value = ResultObject.loading()
            println("Corrutina lanzada en $this")
            block().collect { data ->
                if (data == null) {
                    stateFlow.value = ResultObject.empty()
                } else {
                    stateFlow.value = ResultObject.success(data)
                }
            }
        } catch (exception: Exception) {
            if (exception is CancellationException) {
                println("Corrutina cancelada en $this")
            } else {
                exception.printStackTrace()
                stateFlow.value = ResultObject.error(exception)
            }
        }
    }
}

fun <T> ViewModel.launchData(
    stateFlow: MutableStateFlow<ResultObject<T>>? = null,
    block: suspend () -> T
) {
    viewModelScope.launch {
        try {
            //  stateFlow?.value = ResultObject.loading()
            println("Corrutina lanzada en $this")
            val data = block()
            if (data == null) {
                stateFlow?.value = ResultObject.empty()
            } else {
                stateFlow?.value = ResultObject.success(data)
            }
        } catch (exception: Exception) {
            if (exception is CancellationException) {
                println("Corrutina cancelada en $this")
            } else {
                exception.printStackTrace()
                stateFlow?.value = ResultObject.error(exception)
            }
        }
    }
}
