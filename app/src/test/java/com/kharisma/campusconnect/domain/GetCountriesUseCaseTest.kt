package com.kharisma.campusconnect.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCountriesUseCaseTest {
    private val expected = Result.Success(
        listOf(
            NegaraMitra("Indonesia", "ID", "Jakarta"),
            NegaraMitra("Jepang", "JP", "Tokyo")
        )
    )
    private val repository = FakeInfoMitraRepository(expected)
    private val useCase = GetCountriesUseCase(repository)

    @Test
    fun invoke_mengembalikanHasilDariRepository() = runTest {
        assertEquals(expected, useCase())
        assertEquals(1, repository.jumlahPemanggilan)
    }

    private class FakeInfoMitraRepository(
        private val hasil: Result<List<NegaraMitra>>
    ) : InfoMitraRepository {
        var jumlahPemanggilan: Int = 0
            private set

        override suspend fun getCountries(): Result<List<NegaraMitra>> {
            jumlahPemanggilan++
            return hasil
        }
    }
}
