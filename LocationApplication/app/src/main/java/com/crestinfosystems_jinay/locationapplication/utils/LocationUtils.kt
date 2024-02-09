package com.crestinfosystems_jinay.locationapplication.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.crestinfosystems_jinay.locationapplication.data.LocationData
import com.crestinfosystems_jinay.locationapplication.model.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {

    private val _fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate(viewModel: LocationViewModel) {
//        val locationCallBack = object : LocationCallback() {
//            override fun onLocationResult(locationRessult: LocationResult) {
//                super.onLocationResult(locationRessult)
//                locationRessult.lastLocation?.let {
//                    Log.d("Location","Lat :: ${it.latitude} Long :: ${it.longitude}")
//                    val location = LocationData(lat = it.latitude, long = it.longitude)
//                    viewModel.updateLocation(location)
//                }
//            }
//        }

//        val locationRequest = LocationRequest.Builder(
//            Priority.PRIORITY_HIGH_ACCURACY, 1000
//        ).    build()
        _fusedLocationClient.lastLocation.addOnSuccessListener { location->
            val location = LocationData(lat = location.latitude, long = location.longitude)
            viewModel.updateLocation(location)
        }
//        _fusedLocationClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.getMainLooper())
    }

    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun reverseGeoCoding(locationData: LocationData):String{
        val geocoder = Geocoder(context, Locale.getDefault())
        val cordinates = LatLng(locationData.lat,locationData.long)
        val address: MutableList<Address>? = geocoder.getFromLocation(cordinates.latitude,cordinates.longitude,1)
        return if(address != null){
            address.get(0).getAddressLine(0)
        }else{
            "Address Not Found"
        }

    }
}