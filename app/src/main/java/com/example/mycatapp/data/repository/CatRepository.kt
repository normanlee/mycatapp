package com.example.mycatapp.data.repository

import com.example.mycatapp.data.CatApiService
import com.example.mycatapp.data.ImageDto

// We pass the CatApiService through the constructor
class CatRepository(
    private val apiService: CatApiService
) {

    suspend fun getRandomCats(page: Int? = 0): List<ImageDto> {
        // You can now call any methods defined in your interface!
        return apiService.searchImages(page = page, limit = 20, order = "DESC")
    }

    // You can add more repository methods here that use apiService
}
