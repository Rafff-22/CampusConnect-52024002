package com.kharisma.campusconnect.presentation

import androidx.lifecycle.ViewModel
import com.kharisma.campusconnect.domain.GetCoursesUseCase
import com.kharisma.campusconnect.shared.MataKuliah
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {
    private val _courses = MutableStateFlow<List<MataKuliah>>(emptyList())
    val courses: StateFlow<List<MataKuliah>> = _courses

    init {
        _courses.value = getCoursesUseCase()
    }
}
