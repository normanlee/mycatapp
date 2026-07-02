package com.example.mycatapp.di

import com.example.mycatapp.data.CatApiService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.mycatapp.BuildConfig

private const val BASE_URL = "https://api.thecatapi.com/v1/"

val appModule = module {
    
    // 1. Provide OkHttpClient with the API key interceptor
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                // Automatically add the API key header to every single request
                val requestWithHeaders = originalRequest.newBuilder()
                    .header("x-api-key", BuildConfig.API_KEY)
                    .build()
                chain.proceed(requestWithHeaders)
            }
            .build()
    }

    // 2. Provide Kotlinx Serialization JSON parser configuration
    single {
        Json {
            ignoreUnknownKeys = true // CRUCIAL: prevents crashing if the Cat API adds new fields later
            coerceInputValues = true
        }
    }

    // 3. Provide the Retrofit client
    single {
        val json = get<Json>()
        val contentType = MediaType.parse("application/json") ?: throw IllegalStateException("Invalid media type")
        
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get()) // This automatically fetches the OkHttpClient we defined in step 1
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    // 4. Provide the CatApiService interface implementation
    single {
        get<Retrofit>().create(CatApiService::class.java)
    }
}
