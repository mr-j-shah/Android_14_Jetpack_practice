package com.crestinfosystems.counterapplication
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterApplication : ViewModel() {
    private val _count = mutableStateOf(0)
    val count : MutableState<Int> = _count
    fun increment() {
        _count.value++
    }

    fun decrement() {
        if(_count.value>0){
            _count.value--
        }
    }
}