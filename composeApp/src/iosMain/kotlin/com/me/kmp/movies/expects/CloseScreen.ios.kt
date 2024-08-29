package com.me.kmp.movies.expects

import platform.UIKit.UIViewController

actual fun closeScreen() {
    val topViewController = UIViewController() // Obtener el controlador actual según tu stack de navegación
    topViewController.dismissViewControllerAnimated(true, null) // Cierra el view controller actual
}