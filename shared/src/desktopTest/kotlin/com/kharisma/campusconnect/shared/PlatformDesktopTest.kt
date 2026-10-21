package com.kharisma.campusconnect.shared

import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformDesktopTest {
    @Test
    fun getPlatformName_returnsDesktop() {
        assertEquals("Desktop/JVM", getPlatformName())
    }
}