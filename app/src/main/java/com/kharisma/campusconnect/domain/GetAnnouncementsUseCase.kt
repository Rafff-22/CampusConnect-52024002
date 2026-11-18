package com.kharisma.campusconnect.domain

import com.kharisma.campusconnect.shared.Pengumuman

class GetAnnouncementsUseCase(private val repository: AnnouncementRepository) {
    suspend operator fun invoke(): Result<List<Pengumuman>> = repository.getAnnouncements()
}
