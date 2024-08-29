package com.me.kmp.movies.expects

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.staticCompositionLocalOf
import org.koin.core.context.GlobalContext
import org.koin.java.KoinJavaComponent.inject

// @Composable
actual fun openAppSettings() {
    val context: Context = GlobalContext.get().get() // Recuperar el Contexto usando Koin
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}
