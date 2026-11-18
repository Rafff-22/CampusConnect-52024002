package com.kharisma.campusconnect.domain

import com.kharisma.campusconnect.shared.Pengumuman

interface AnnouncementRepository {
    suspend fun getAnnouncements(): Result<List<Pengumuman>>
}
