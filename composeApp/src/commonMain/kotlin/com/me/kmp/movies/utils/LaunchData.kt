package com.me.kmp.movies.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/*
* This function is ideal for the first call to a flow, this will return the first value of the flow
* is util for one shot calls as record creation, update, delete, etc.
*/
fun <T> ViewModel.launchFirstFlow(
    stateFlow: MutableStateFlow<ResultObject<T>>,
    block: suspend () -> Flow<T?>
) {
    viewModelScope.launch {
        try {
            // stateFlow.value = ResultObject.loading()
            println("Corrutina lanzada en $this")
            val data = block().firstOrNull()
            if (data == null) {
                stateFlow.value = ResultObject.empty()
            } else {
                stateFlow.value = ResultObject.success(data)
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

/*
* This function is used to launch a flow and handle the result in a stateFlow updated
* then this return a StateFlow<ResultObject<T>> to be observed in the UI
*/
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
                println("Corrutina cancelada en $this")
                exception.printStackTrace()
                stateFlow.value = ResultObject.error(exception)
            }
        }
    }
}

/*
* This function is used to launch a flow and handle the result in a stateFlow
* is optional to pass a stateFlow, if you don't pass a stateFlow, the result will not be updated in a stateFlow
* util for one shot calls as record creation, update, delete, etc.
*/

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
