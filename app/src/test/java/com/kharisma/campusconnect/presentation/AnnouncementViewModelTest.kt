package com.kharisma.campusconnect.presentation

import com.kharisma.campusconnect.domain.AnnouncementRepository
import com.kharisma.campusconnect.domain.GetAnnouncementsUseCase
import com.kharisma.campusconnect.domain.Result
import com.kharisma.campusconnect.shared.Pengumuman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnnouncementViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun stateAkhirSuccess() = runTest(dispatcher) {
        val daftar = listOf(Pengumuman(1, "Judul", "Isi"))
        val viewModel = AnnouncementViewModel(
            GetAnnouncementsUseCase(FakeAnnouncementRepository(Result.Success(daftar)))
        )

        advanceUntilIdle()

        assertEquals(AnnouncementUiState.Success(daftar), viewModel.uiState.value)
    }

    @Test
    fun stateError() = runTest(dispatcher) {
        val viewModel = AnnouncementViewModel(
            GetAnnouncementsUseCase(FakeAnnouncementRepository(Result.Error("Gagal memuat")))
        )

        advanceUntilIdle()

        assertEquals(AnnouncementUiState.Error("Gagal memuat"), viewModel.uiState.value)
    }

    @Test
    fun muatUlang_memuatUlangData() = runTest(dispatcher) {
        val awal = listOf(Pengumuman(1, "Awal", "Isi awal"))
        val baru = listOf(Pengumuman(2, "Baru", "Isi baru"))
        val repository = FakeAnnouncementRepository(Result.Success(awal))
        val viewModel = AnnouncementViewModel(GetAnnouncementsUseCase(repository))

        advanceUntilIdle()
        assertEquals(AnnouncementUiState.Success(awal), viewModel.uiState.value)

        repository.hasil = Result.Success(baru)
        viewModel.muatUlang()
        advanceUntilIdle()

        assertEquals(2, repository.jumlahPemanggilan)
        assertEquals(AnnouncementUiState.Success(baru), viewModel.uiState.value)
    }

    private class FakeAnnouncementRepository(
        hasil: Result<List<Pengumuman>>
    ) : AnnouncementRepository {
        var hasil: Result<List<Pengumuman>> = hasil
        var jumlahPemanggilan: Int = 0
            private set

        override suspend fun getAnnouncements(): Result<List<Pengumuman>> {
            jumlahPemanggilan++
            return hasil
        }
    }
}
