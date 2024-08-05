package com.me.kmp.movies.data.repository

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.FloatRange
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import androidx.annotation.IntRange
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class RegionRepositoryImpl(
    private val geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient
) : RegionRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getRegion(): String = withContext(Dispatchers.IO) {
        getLastKnownLocation()?.let { location ->
            getCountryCode(location.latitude, location.longitude) ?: DEFAULT_REGION
        } ?: DEFAULT_REGION
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location? = withContext(Dispatchers.IO) {
        try {
            fusedLocationClient.lastLocation.await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun getCountryCode(
        latitude: Double,
        longitude: Double
    ): String? = withContext(Dispatchers.IO) {
        try {
            val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
            addresses.firstOrNull()?.countryCode
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

@Suppress("DEPRECATION")
suspend fun Geocoder.getFromLocationCompat(
    @FloatRange(from = -90.0, to = 90.0) latitude: Double,
    @FloatRange(from = -180.0, to = 180.0) longitude: Double,
    @IntRange maxResults: Int
): List<Address> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    suspendCancellableCoroutine { continuation ->
        getFromLocation(latitude, longitude, maxResults) {
            continuation.resume(it)
        }
    }
} else {
    withContext(Dispatchers.IO) {
        getFromLocation(latitude, longitude, maxResults) ?: emptyList()
    }
}
