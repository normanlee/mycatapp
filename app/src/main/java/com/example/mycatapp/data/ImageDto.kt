package com.example.mycatapp.data

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<BreedDto>? = null,
    val categories: List<CategoryDto>? = null
)
