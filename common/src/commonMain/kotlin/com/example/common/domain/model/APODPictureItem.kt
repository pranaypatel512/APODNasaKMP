package com.example.common.domain.model


/**
 * API response item and database table for storing media items with details
 */
data class APODPictureItem(
    var url: String,
    var copyright: String? = null,
    var date: String? = null,
    var explanation: String? = null,
    var hdurl: String? = null,
    var media_type: String? = null,
    var service_version: String? = null,
    var thumbnail_url: String? = null,
    var title: String? = null
) {
    fun isVideo(): Boolean = media_type == MediaType.video.toString()
}