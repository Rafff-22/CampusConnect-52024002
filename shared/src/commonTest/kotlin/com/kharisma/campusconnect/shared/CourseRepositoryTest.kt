package com.kharisma.campusconnect.shared

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CourseRepositoryTest {
    private val repository = CourseRepositoryImpl()

    @Test
    fun getAllCourses_returnsFiveItems() {
        val courses = repository.getAllCourses()
        assertEquals(5, courses.size)
    }

    @Test
    fun getAllCourses_allIdsAreUnique() {
        val courses = repository.getAllCourses()
        val ids = courses.map { it.id }.distinct()
        assertEquals(courses.size, ids.size)
    }

    @Test
    fun getAllCourses_allSksInRange() {
        val courses = repository.getAllCourses()
        courses.forEach { course ->
            assertTrue(course.sks in 1..6, "SKS ${course.sks} for course ${course.nama} is not in range 1..6")
        }
    }

    @Test
    fun getAllCourses_allNamesNonEmpty() {
        val courses = repository.getAllCourses()
        courses.forEach { course ->
            assertTrue(course.nama.isNotBlank(), "Course name is empty for id ${course.id}")
        }
    }

    @Test
    fun getAllCourses_allDosenNonEmpty() {
        val courses = repository.getAllCourses()
        courses.forEach { course ->
            assertTrue(course.dosen.isNotBlank(), "Dosen name is empty for id ${course.id}")
        }
    }
}