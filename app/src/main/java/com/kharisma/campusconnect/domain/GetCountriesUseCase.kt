package com.kharisma.campusconnect.domain

class GetCountriesUseCase(private val repository: InfoMitraRepository) {
    suspend operator fun invoke(): Result<List<NegaraMitra>> = repository.getCountries()
}
