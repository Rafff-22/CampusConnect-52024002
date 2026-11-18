package com.kharisma.campusconnect.data.repository

import com.apollographql.apollo.ApolloClient
import com.kharisma.campusconnect.domain.InfoMitraRepository
import com.kharisma.campusconnect.domain.NegaraMitra
import com.kharisma.campusconnect.domain.Result
import com.kharisma.campusconnect.graphql.GetCountriesQuery
import kotlinx.coroutines.CancellationException

class InfoMitraRepositoryImpl(private val apolloClient: ApolloClient) : InfoMitraRepository {
    override suspend fun getCountries(): Result<List<NegaraMitra>> = try {
        val response = apolloClient.query(GetCountriesQuery()).execute()
        if (response.hasErrors()) {
            val pesan = response.errors.orEmpty().joinToString("; ") { it.message }
            Result.Error(pesan)
        } else {
            val daftar = response.data?.countries.orEmpty().map { negara ->
                NegaraMitra(
                    nama = negara.name,
                    kode = negara.code,
                    ibuKota = negara.capital ?: "-"
                )
            }
            Result.Success(daftar)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.Error("Gagal mengambil data negara: ${e.message}")
    }
}
