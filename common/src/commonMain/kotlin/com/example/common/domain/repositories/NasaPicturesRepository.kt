package com.example.common.domain.repositories

import com.example.common.domain.NetworkResultState
import com.example.common.domain.model.APODPictureItem
import kotlinx.coroutines.flow.Flow

interface NasaPicturesRepository {
    suspend fun getAPODPictures(count:Int=10
    ): Flow<NetworkResultState<List<APODPictureItem>?>>
}