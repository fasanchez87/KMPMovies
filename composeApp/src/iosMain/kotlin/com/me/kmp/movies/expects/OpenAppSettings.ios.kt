package com.me.kmp.movies.expects

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

//@Composable
actual fun openAppSettings() {
    val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
    if (settingsUrl != null) {
        UIApplication.sharedApplication.openURL(settingsUrl)
    }
}
