package com.kharisma.campusconnect

import java.io.File
import org.junit.Assert.assertFalse
import org.junit.Test

class ArchitectureRuleTest {
    private val mainDir = File("src/main/java/com/kharisma/campusconnect")

    @Test
    fun presentationTidakAksesRepositoryLangsung() {
        val presentationDir = File(mainDir, "presentation")
        val files = presentationDir.listFiles() ?: emptyArray()
        files.forEach { file ->
            val content = file.readText()
            val salah = content.contains("import com.kharisma.campusconnect.shared.CourseRepository") ||
                content.contains("CourseRepositoryImpl")
            assertFalse(
                "File presentation ${file.name} tidak boleh mengakses CourseRepository/CourseRepositoryImpl langsung (wajib lewat UseCase)",
                salah
            )
        }
    }

    @Test
    fun domainTidakAksesAndroidPresentationDi() {
        val domainDir = File(mainDir, "domain")
        val files = domainDir.listFiles() ?: emptyArray()
        files.forEach { file ->
            val content = file.readText()
            val salah = content.contains("import android.") ||
                content.contains("com.kharisma.campusconnect.presentation") ||
                content.contains("com.kharisma.campusconnect.di")
            assertFalse(
                "File domain ${file.name} tidak boleh mengimpor android.*, presentation, atau di",
                salah
            )
        }
    }
}
