package com.crestinfosystems_jinay.locationapplication

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.crestinfosystems_jinay.locationapplication.data.LocationData
import com.crestinfosystems_jinay.locationapplication.model.LocationViewModel
import com.crestinfosystems_jinay.locationapplication.ui.theme.LocationApplicationTheme
import com.crestinfosystems_jinay.locationapplication.utils.LocationUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val locationViewModel: LocationViewModel = viewModel()
    locationScreen(context = context, locationUtils = locationUtils, viewModel = locationViewModel)
}

@Composable
fun locationScreen(
    locationUtils: LocationUtils,
    context: Context,
    viewModel: LocationViewModel
) {
    val location = viewModel.location.value
    val Address = location?.let {
        locationUtils.reverseGeoCoding(it?: LocationData(lat = 0.0, long = 0.0))
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                locationUtils.requestLocationUpdate(viewModel = viewModel)
            } else {
                val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (rationalRequired) {
                    Toast.makeText(
                        context,
                        "Location Permission required for this feature to Work",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Location Permission required, Please enable it in Android Settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (location != null) {
            Text(text = "Location\nLat :: ${location.lat}\nLong :: ${location.long}\nAddress :: ${Address}")
        } else {
            Text(text = "Location not Avalible")

        }
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
               locationUtils.requestLocationUpdate(viewModel=viewModel)
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Get Location")
        }
    }

}
