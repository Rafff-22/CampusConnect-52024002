package com.kharisma.campusconnect.presentation

import com.kharisma.campusconnect.domain.GetCoursesUseCase
import com.kharisma.campusconnect.shared.CourseRepository
import com.kharisma.campusconnect.shared.MataKuliah
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest {
    private val useCase = GetCoursesUseCase(FakeCourseRepository)
    private val viewModel = HomeViewModel(useCase)

    @Test
    fun courses_valueIsFilledFromUseCase() {
        val expected = FakeCourseRepository.DATA
        assertEquals(expected.size, viewModel.courses.value.size)
        assertEquals(expected, viewModel.courses.value)
    }

    private object FakeCourseRepository : CourseRepository {
        val DATA = listOf(
            MataKuliah(201, "Pemrograman Berorientasi Objek", 3, "Dosen X"),
            MataKuliah(202, "Sistem Basis Data", 3, "Dosen Y")
        )
        override fun getAllCourses(): List<MataKuliah> = DATA
    }
}
