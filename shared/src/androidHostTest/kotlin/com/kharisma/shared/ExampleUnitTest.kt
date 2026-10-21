package com.kharisma.shared

import com.kharisma.campusconnect.shared.getPlatformName
import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleUnitTest {
    @Test
    fun getPlatformName_returnsAndroid() {
        assertEquals("Android", getPlatformName())
    }
}