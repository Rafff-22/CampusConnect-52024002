package com.kharisma.campusconnect.domain

import com.kharisma.campusconnect.shared.Pengumuman
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAnnouncementsUseCaseTest {
    private val expected = Result.Success(
        listOf(
            Pengumuman(1, "Pengumuman pertama", "Isi pertama"),
            Pengumuman(2, "Pengumuman kedua", "Isi kedua")
        )
    )
    private val repository = FakeAnnouncementRepository(expected)
    private val useCase = GetAnnouncementsUseCase(repository)

    @Test
    fun invoke_mengembalikanHasilDariRepository() = runTest {
        assertEquals(expected, useCase())
        assertEquals(1, repository.jumlahPemanggilan)
    }

    private class FakeAnnouncementRepository(
        private val hasil: Result<List<Pengumuman>>
    ) : AnnouncementRepository {
        var jumlahPemanggilan: Int = 0
            private set

        override suspend fun getAnnouncements(): Result<List<Pengumuman>> {
            jumlahPemanggilan++
            return hasil
        }
    }
}
