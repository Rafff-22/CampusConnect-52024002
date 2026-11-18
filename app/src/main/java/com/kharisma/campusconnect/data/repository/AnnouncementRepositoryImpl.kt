package com.kharisma.campusconnect.data.repository

import com.kharisma.campusconnect.data.remote.AnnouncementApi
import com.kharisma.campusconnect.domain.AnnouncementRepository
import com.kharisma.campusconnect.domain.Result
import com.kharisma.campusconnect.shared.Pengumuman
import java.io.IOException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

class AnnouncementRepositoryImpl(private val api: AnnouncementApi) : AnnouncementRepository {
    override suspend fun getAnnouncements(): Result<List<Pengumuman>> = try {
        val daftar = api.getAnnouncements().map { dto ->
            Pengumuman(id = dto.id, judul = dto.title, isi = dto.body)
        }
        Result.Success(daftar)
    } catch (e: IOException) {
        Result.Error("Tidak dapat terhubung ke server. Periksa koneksi internet.")
    } catch (e: HttpException) {
        Result.Error("Server mengembalikan error HTTP ${e.code()}.")
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.Error("Gagal memuat pengumuman: ${e.message ?: "Kesalahan tidak diketahui"}")
    }
}
