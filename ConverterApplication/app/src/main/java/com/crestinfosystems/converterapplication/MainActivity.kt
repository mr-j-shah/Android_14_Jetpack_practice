package com.crestinfosystems.converterapplication

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crestinfosystems.converterapplication.ui.theme.ConverterApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConverterApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainFunction()
                }
            }
        }
    }
}


@Composable
fun MainFunction() {
    var unitValue: String by remember {
        mutableStateOf("")
    }
    var result: String by remember {
        mutableStateOf("")
    }
    var itemList = listOf<String>("Meter", "Kilometer", "Centimeter", "Millimeter")
    var selectedItem by remember { mutableStateOf("Select") }
    var expanded1 by remember { mutableStateOf(false) }
    var selectedItem2 by remember { mutableStateOf("Select") }
    var expanded2 by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "Unit Converter", fontSize = 24.sp, fontWeight = FontWeight.W600)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = unitValue,
            onValueChange = {
                unitValue = it
                result = "Result :: ${converter(selectedItem, selectedItem2, unitValue.toInt())}"
            },
            shape = RoundedCornerShape(8.dp),
            isError = isFieldEmpty(unitValue),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            label = { Text("Enter Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Box {
                Button(onClick = { expanded1 = true }) {
                    Text(text = selectedItem)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",

                        )
                }
                DropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {

                    itemList.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedItem = item
                                expanded1 = false
                            })
                    }
                }
            }
            Box {
                Button(onClick = { expanded2 = true }) {
                    Text(text = selectedItem2)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",

                        )
                }
                DropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {

                    itemList.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedItem2 = item
                                expanded2 = false
                            })
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600
        )
    }
}

fun isFieldEmpty(value: String): Boolean {
    return value.isEmpty()
}

fun converter(sel1: String, sel2: String, value: Int): String {
    if (sel1 == "Meter") {
        when (sel2) {
            "Meter" -> return "${value}Meter is ${value.toFloat()}Meter"
            "Kilometer" -> return ("${value}Meter is ${(value / 1000).toFloat()}Kilometer")
            "Centimeter" -> return ("${value}Meter is ${(value * 100).toFloat()}Centimeter")
            else -> return ("${value}Meter is ${(value * 1000).toFloat()}Millimeter")
        }
    } else if (sel1 == "Kilometer") {
        when (sel2) {
            "Meter" -> return ("${value}Kilometer is ${(value * 1000).toFloat()}Meter")
            "Kilometer" -> return ("${value}Kilometer is ${value.toFloat()}Kilometer")
            "Centimeter" -> return ("${value}Kilometer is ${(value * 100000).toFloat()}Centimeter")
            else -> return ("${value}Kilometer is ${(value * 1000000).toFloat()}Millimeter")
        }
    } else if (sel1 == "Centimeter") {
        when (sel2) {
            "Meter" -> return ("${value}Centimeter is ${(value / 100).toFloat()}Meter")
            "Kilometer" -> return ("${value}Centimeter is ${(value / 100000).toFloat()}Kilometer")
            "Centimeter" -> return ("${value}Centimeter is ${value.toFloat()}Centimeter")
            else -> return ("${value}Centimeter is ${(value * 10).toFloat()}Millimeter")
        }
    } else {
        when (sel2) {
            "Meter" -> return ("${value}Millimeter is ${(value / 1000).toFloat()}Meter")
            "Kilometer" -> return ("${value}Millimeter is ${(value / 1000000).toFloat()}Kilometer")
            "Centimeter" -> return ("${value}Millimeter is ${(value / 10).toFloat()}Centimeter")
            else -> return ("${value}Millimeter is ${value.toFloat()}Millimeter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConverterApplicationTheme {
        MainFunction()
    }
}