package com.example.weatherapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Data.new_weather
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModelClass : ViewModel() {

    private val _apiState = mutableStateOf(WeatherApiState())
    val weatherApiState: State<WeatherApiState> = _apiState;

    //    val cityName = mutableStateOf("Surat")
    init {
        fetchData("Surat")
    }

    fun fetchData(cityName: String) {
        viewModelScope.launch {
            _apiState.value = _apiState.value.copy(
                loading = true,
            )
            try {
                val response =
                    weatherApiService.getWeather(
                        "491c2b65732e6290f8fd3b7322077383", cityName
                    )
                Log.d("TAG", response.toString())
                _apiState.value = _apiState.value.copy(
                    respo = response,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _apiState.value = _apiState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
            }
        }
    }

    data class WeatherApiState(
        val loading: Boolean = true,
        val respo: new_weather? = null,
        val error: String? = null
    )
}