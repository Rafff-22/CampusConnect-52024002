package com.kharisma.campusconnect.presentation

import com.kharisma.campusconnect.domain.GetCountriesUseCase
import com.kharisma.campusconnect.domain.InfoMitraRepository
import com.kharisma.campusconnect.domain.NegaraMitra
import com.kharisma.campusconnect.domain.Result
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
class InfoMitraViewModelTest {
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
        val daftar = listOf(NegaraMitra("Indonesia", "ID", "Jakarta"))
        val viewModel = InfoMitraViewModel(
            GetCountriesUseCase(FakeInfoMitraRepository(Result.Success(daftar)))
        )

        advanceUntilIdle()

        assertEquals(InfoMitraUiState.Success(daftar), viewModel.uiState.value)
    }

    @Test
    fun stateError() = runTest(dispatcher) {
        val viewModel = InfoMitraViewModel(
            GetCountriesUseCase(FakeInfoMitraRepository(Result.Error("Gagal mengambil data negara")))
        )

        advanceUntilIdle()

        assertEquals(
            InfoMitraUiState.Error("Gagal mengambil data negara"),
            viewModel.uiState.value
        )
    }

    @Test
    fun muatUlang_memuatUlangData() = runTest(dispatcher) {
        val awal = listOf(NegaraMitra("Indonesia", "ID", "Jakarta"))
        val baru = listOf(
            NegaraMitra("Indonesia", "ID", "Jakarta"),
            NegaraMitra("Jepang", "JP", "Tokyo")
        )
        val repository = FakeInfoMitraRepository(Result.Success(awal))
        val viewModel = InfoMitraViewModel(GetCountriesUseCase(repository))

        advanceUntilIdle()
        assertEquals(InfoMitraUiState.Success(awal), viewModel.uiState.value)

        repository.hasil = Result.Success(baru)
        viewModel.muatUlang()
        advanceUntilIdle()

        assertEquals(2, repository.jumlahPemanggilan)
        assertEquals(InfoMitraUiState.Success(baru), viewModel.uiState.value)
    }

    private class FakeInfoMitraRepository(
        hasil: Result<List<NegaraMitra>>
    ) : InfoMitraRepository {
        var hasil: Result<List<NegaraMitra>> = hasil
        var jumlahPemanggilan: Int = 0
            private set

        override suspend fun getCountries(): Result<List<NegaraMitra>> {
            jumlahPemanggilan++
            return hasil
        }
    }
}
