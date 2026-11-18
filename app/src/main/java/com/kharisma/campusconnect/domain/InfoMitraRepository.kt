package com.kharisma.campusconnect.domain

interface InfoMitraRepository {
    suspend fun getCountries(): Result<List<NegaraMitra>>
}
