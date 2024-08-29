package com.me.kmp.movies.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
fun PermissionRequestEffect(
    permission: Permission,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionDeniedAlways: () -> Unit
) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    LaunchedEffect(controller) {
        try {
            controller.providePermission(permission)
            if (controller.isPermissionGranted(permission)) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        } catch (e: DeniedAlwaysException) {
            onPermissionDeniedAlways()
        } catch (e: DeniedException) {
            onPermissionDenied()
        }
    }
}
