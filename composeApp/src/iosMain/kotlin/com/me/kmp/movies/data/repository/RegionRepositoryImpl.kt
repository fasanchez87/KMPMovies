package com.me.kmp.movies.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.CLPlacemark
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RegionRepositoryImpl : RegionRepository {

    val locationManager = CLLocationManager()

    override suspend fun getRegion(): String {
        Napier.d("region from iOS: ${getCurrentLocation()?.toRegion() ?: DEFAULT_REGION}")
        return getCurrentLocation()?.toRegion() ?: DEFAULT_REGION
    }

    private suspend fun getCurrentLocation(): CLLocation? =
        suspendCancellableCoroutine { continuation ->
            
            val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {

                override fun locationManager(
                    manager: CLLocationManager,
                    didUpdateLocations: List<*>
                ) {
                    val location = didUpdateLocations.lastOrNull() as? CLLocation
                    locationManager.stopUpdatingLocation()
                    continuation.resume(location)
                }

                override fun locationManager(
                    manager: CLLocationManager,
                    didFailWithError: NSError
                ) {
                    continuation.resume(null)
                    locationManager.stopUpdatingLocation()
                    continuation.resumeWithException(
                        RuntimeException(didFailWithError.localizedDescription)
                    )
                }
            }

            continuation.invokeOnCancellation {
                locationManager.stopUpdatingLocation()
            }

            locationManager.delegate = delegate
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()
        }

    private suspend fun CLLocation.toRegion(): String =
        suspendCancellableCoroutine { continuation ->
            val geocoder = CLGeocoder()
            geocoder.reverseGeocodeLocation(this) { placemark, error ->
                if (error != null || placemark == null) {
                    continuation.resume(DEFAULT_REGION)
                } else {
                    val countryCode = placemark.firstOrNull()?.let { (it as CLPlacemark).ISOcountryCode }
                    continuation.resume(countryCode ?: DEFAULT_REGION)
                }
            }
        }
}
