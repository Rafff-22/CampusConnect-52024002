package com.kharisma.campusconnect

import java.io.File
import org.junit.Assert.assertFalse
import org.junit.Test

class ArchitectureRuleTest {
    private val mainDir = File("src/main/java/com/kharisma/campusconnect")

    private fun File.kotlinFiles(): List<File> =
        if (!exists()) emptyList()
        else walkTopDown().filter { it.isFile && it.extension == "kt" }.toList()

    private fun File.importStatements(): List<String> =
        readLines().map { it.trim() }.filter { it.startsWith("import ") }

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

    @Test
    fun presentationTidakMengimporRepositoryAtauPustakaJaringan() {
        val presentationDir = File(mainDir, "presentation")
        val files = presentationDir.kotlinFiles()
        assertFalse("Folder presentation tidak ditemukan", files.isEmpty())

        files.forEach { file ->
            val pelanggaran = file.importStatements().filter { baris ->
                val tipe = baris.removePrefix("import ").substringAfterLast('.')
                tipe.endsWith("Repository") ||
                    tipe.endsWith("RepositoryImpl") ||
                    baris.contains("retrofit2") ||
                    baris.contains("com.apollographql")
            }
            assertFalse(
                "File presentation ${file.name} tidak boleh mengimpor Repository/RepositoryImpl, retrofit2, atau com.apollographql: $pelanggaran",
                pelanggaran.isNotEmpty()
            )
        }
    }

    @Test
    fun domainTidakMengimporAndroidJaringanAtauLayerLain() {
        val domainDir = File(mainDir, "domain")
        val files = domainDir.kotlinFiles()
        assertFalse("Folder domain tidak ditemukan", files.isEmpty())

        val polaTerlarang = listOf(
            "import android.",
            "retrofit2",
            "okhttp3",
            "com.apollographql",
            "com.kharisma.campusconnect.data",
            "com.kharisma.campusconnect.presentation",
            "com.kharisma.campusconnect.di"
        )

        files.forEach { file ->
            val pelanggaran = file.importStatements().filter { baris ->
                polaTerlarang.any { baris.contains(it) }
            }
            assertFalse(
                "File domain ${file.name} tidak boleh mengimpor android.*, retrofit2, okhttp3, com.apollographql, data, presentation, atau di: $pelanggaran",
                pelanggaran.isNotEmpty()
            )
        }
    }
}
