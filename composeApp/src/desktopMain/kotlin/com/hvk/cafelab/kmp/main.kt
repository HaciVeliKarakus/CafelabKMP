package com.hvk.cafelab.kmp

import App
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "CafelabKMP",
    ) {
        App()
    }
}