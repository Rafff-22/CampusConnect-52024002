package com.kharisma.campusconnect.domain

import com.kharisma.campusconnect.shared.CourseRepository
import com.kharisma.campusconnect.shared.MataKuliah

class GetCoursesUseCase(private val repository: CourseRepository) {
    operator fun invoke(): List<MataKuliah> = repository.getAllCourses()
}
