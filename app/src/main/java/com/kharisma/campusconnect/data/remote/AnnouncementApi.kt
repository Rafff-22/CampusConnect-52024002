package com.kharisma.campusconnect.data.remote

import retrofit2.http.GET

interface AnnouncementApi {
    @GET("posts")
    suspend fun getAnnouncements(): List<AnnouncementDto>
}
