package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.Data.Current
import com.example.weatherapp.Data.Location
import com.example.weatherapp.Data.Request
import com.example.weatherapp.Data.new_weather

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun weatherScreen() {
    val weatherModel: ViewModelClass = viewModel()
    var cityName by remember {
        mutableStateOf("Surat")
    }
    val viewstate by weatherModel.weatherApiState
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = cityName,
            onValueChange = {
                cityName = it
            },
            label = {
                Text(text = "City Name")
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        cityName = ""
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    weatherModel.fetchData(cityName.trim())
                    keyboardController?.hide()
                },
            ),
        )
//        Spacer(modifier = Modifier.height(5.dp))
//        Button(onClick = {  }) {
//            Text(text = "Submit")
//        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                viewstate.loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                viewstate.error != null -> {
                    Text(viewstate.error.toString())
                }

                else -> {
                    resultScreen(viewstate.respo)
                }
            }
        }
    }
}

@Preview
@Composable
fun myPreview() {
    resultScreen(
        new_weather(
            location = Location(
                name = "Navsari",
                country = "India",
                region = "Gujarat",
                lat = "20.850",
                localtime = "2024-01-11 13:46",
                lon = "72.917",
                timezone_id = "Asia/Kolkata",
                localtime_epoch = 1704980760,
                utc_offset = "5.50"
            ), current = Current(
                observation_time = "08:16 AM",
                temperature = 30,
                weather_code = 113,
                weather_icons = listOf(
                    "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png"
                ),
                weather_descriptions = listOf(
                    "Sunny"
                ),
                wind_speed = 13,
                wind_degree = 336,
                wind_dir = "NNW",
                pressure = 1012,
                precip = 0,
                humidity = 46,
                cloudcover = 4,
                feelslike = 32,
                uv_index = 8,
                visibility = 10,
                is_day = "yes"
            ), request = Request(
                type = "City",
                query = "Navsari, India",
                language = "en",
                unit = "m"
            )
        ),
    )
}

@Composable
fun resultScreen(data: new_weather?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            Image(
//                painter = rememberAsyncImagePainter(data!!.current.weather_icons.first().trim().toString()),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize(fraction = 0.2F)
//
//            )
            Column(modifier = Modifier.fillMaxWidth(fraction = 0.6F)) {
                Text(
                    text = data!!.location.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier

                        .padding(top = 8.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                )
                Text(
                    text = "${data.location.region}, ${data.location.country}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier

                        .padding(top = 0.dp, bottom = 0.dp, start = 4.dp, end = 4.dp)
                )
            }
            Text(
                text = "${data!!.current.temperature} \u2103",
                fontSize = 32.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
            )
        }
        Text(
            text = "${data!!.current.weather_descriptions.first()}",
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 0.dp, start = 12.dp, end = 8.dp)
        )
        Text(
            text = "Wind Speed ${data!!.current.wind_speed} km/h in ${data.current.wind_dir} with Degree of ${data.current.wind_degree}",
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 0.dp, start = 12.dp, end = 8.dp)
        )
        Text(
            text = "Pressure = ${data.current.pressure} hPa",
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 0.dp, start = 12.dp, end = 8.dp)
        )
        Text(
            text = "uv Index = ${data.current.uv_index}",
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 0.dp, start = 12.dp, end = 8.dp)
        )
    }
}