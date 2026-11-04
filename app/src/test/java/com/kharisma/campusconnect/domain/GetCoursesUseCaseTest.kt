package com.kharisma.campusconnect.domain

import com.kharisma.campusconnect.shared.CourseRepository
import com.kharisma.campusconnect.shared.MataKuliah
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCoursesUseCaseTest {
    private val useCase = GetCoursesUseCase(FakeCourseRepository)

    @Test
    fun invoke_returnsSameDataAsRepository() {
        val expected = FakeCourseRepository.DATA
        val actual = useCase()
        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    private object FakeCourseRepository : CourseRepository {
        val DATA = listOf(
            MataKuliah(101, "Struktur Data", 3, "Dosen A"),
            MataKuliah(102, "Algoritma dan Pemrograman", 4, "Dosen B")
        )
        override fun getAllCourses(): List<MataKuliah> = DATA
    }
}
