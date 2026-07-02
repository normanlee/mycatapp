package com.example.mycatapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavouriteDto(
    val id: Int,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("image_id") val imageId: String,
    @SerialName("sub_id") val subId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val image: ImageDto? = null
)
