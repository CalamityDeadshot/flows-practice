package com.app.flowspractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val DEFAULT_VALUE = "Hello world!"


class ComparisonViewModel : ViewModel() {

    private val _liveData = MutableLiveData(DEFAULT_VALUE)
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow(DEFAULT_VALUE)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    var errorCounter = 0

    fun onLiveDataClick() {
        _liveData.value = "LiveData"
    }

    fun onStateFlowClick() {
        errorCounter++
        _stateFlow.value = "Error $errorCounter"
    }

    fun onSharedFlowClick() = viewModelScope.launch {
        errorCounter++
        _sharedFlow.emit("Error $errorCounter")
    }

    fun onFlowClick() = flow<Int> {
        repeat(10) {
            emit(it)
            delay(1000)
        }
    }

}