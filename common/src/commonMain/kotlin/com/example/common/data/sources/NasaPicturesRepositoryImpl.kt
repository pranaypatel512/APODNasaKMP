package com.example.common.data.sources

import com.example.common.domain.NetworkResultState
import com.example.common.domain.model.APODPictureItem
import com.example.common.domain.repositories.NasaPicturesRepository
import com.example.common.utils.safeApiCallHandler
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NasaPicturesRepositoryImpl constructor(
    private val httpClient: HttpClient
) : NasaPicturesRepository {
    override suspend fun getAPODPictures(count:Int): Flow<NetworkResultState<List<APODPictureItem>?>> {
        return flowOf(
            safeApiCallHandler {
                val response = httpClient.get(urlString = "apod/") {
                    parameter("count", count)
                }.body<List<APODPictureItem>?>()
                response
            }
        )
    }

}
