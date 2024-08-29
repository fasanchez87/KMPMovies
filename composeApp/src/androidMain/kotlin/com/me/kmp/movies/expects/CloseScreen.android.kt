package com.me.kmp.movies.expects

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat.finishAffinity
import org.koin.core.context.GlobalContext
import org.koin.java.KoinJavaComponent.inject

actual fun closeScreen() {
    val context: Context = GlobalContext.get().get()
    val activity = context as? Activity
    activity?.let {
        finishAffinity(it)
    }
}