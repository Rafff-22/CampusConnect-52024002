package com.kharisma.campusconnect.data.repository

import com.kharisma.campusconnect.data.remote.AnnouncementApi
import com.kharisma.campusconnect.data.remote.AnnouncementDto
import com.kharisma.campusconnect.domain.Result
import com.kharisma.campusconnect.shared.Pengumuman
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class AnnouncementRepositoryImplTest {
    @Test
    fun sukses_memetakanDtoKePengumuman() = runTest {
        val dto = AnnouncementDto(
            userId = 1,
            id = 10,
            title = "Judul pengumuman",
            body = "Isi pengumuman"
        )
        val repository = AnnouncementRepositoryImpl(FakeAnnouncementApi { listOf(dto) })

        val hasil = repository.getAnnouncements()

        assertEquals(
            Result.Success(listOf(Pengumuman(10, "Judul pengumuman", "Isi pengumuman"))),
            hasil
        )
    }

    @Test
    fun ioException_menghasilkanPesanKoneksi() = runTest {
        val repository = AnnouncementRepositoryImpl(FakeAnnouncementApi { throw IOException("offline") })

        val hasil = repository.getAnnouncements()

        assertEquals(
            Result.Error("Tidak dapat terhubung ke server. Periksa koneksi internet."),
            hasil
        )
    }

    @Test
    fun httpException404_menghasilkanPesanKode() = runTest {
        val response = Response.error<Any>(404, ResponseBody.create(null, "not found"))
        val repository = AnnouncementRepositoryImpl(FakeAnnouncementApi { throw HttpException(response) })

        val hasil = repository.getAnnouncements()

        assertEquals(
            Result.Error("Server mengembalikan error HTTP 404."),
            hasil
        )
    }

    @Test
    fun exceptionUmum_menghasilkanPesanGagal() = runTest {
        val repository = AnnouncementRepositoryImpl(
            FakeAnnouncementApi { throw IllegalStateException("boom") }
        )

        val hasil = repository.getAnnouncements()

        assertEquals(
            Result.Error("Gagal memuat pengumuman: boom"),
            hasil
        )
    }

    private class FakeAnnouncementApi(
        private val aksi: suspend () -> List<AnnouncementDto>
    ) : AnnouncementApi {
        override suspend fun getAnnouncements(): List<AnnouncementDto> = aksi()
    }
}
