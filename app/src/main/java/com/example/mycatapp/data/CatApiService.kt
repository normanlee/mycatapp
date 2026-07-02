package com.example.mycatapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@Serializable
data class FavouriteRequest(
    @SerialName("image_id") val imageId: String,
    @SerialName("sub_id") val subId: String? = null
)

@Serializable
data class FavouriteResponse(
    val message: String,
    val id: Int
)

interface CatApiService {

    // Fetch random cat images
    @GET("images/search")
    suspend fun searchImages(
        @Query("limit") limit: Int = 10,
        @Query("breed_ids") breedIds: String? = null,
        @Query("has_breeds") hasBreeds: Int? = null,
        @Query("order") order: String = "RANDOM" // RANDOM, ASC, DESC
    ): List<ImageDto>

    // Get all cat breeds
    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): List<BreedDto>

    // Get all favourites for the user
    @GET("favourites")
    suspend fun getFavourites(
        @Query("sub_id") subId: String? = null,
        @Query("limit") limit: Int = 10
    ): List<FavouriteDto>

    // Add a new favourite
    @POST("favourites")
    suspend fun addFavourite(
        @Body request: FavouriteRequest
    ): FavouriteResponse

    // Delete a favourite
    @DELETE("favourites/{favouriteId}")
    suspend fun deleteFavourite(
        @Path("favouriteId") favouriteId: Int
    )
}
