package com.raihan.assignment.data.source.network.service

import com.raihan.assignment.BuildConfig
import com.raihan.assignment.data.source.network.model.LoginRequest
import com.raihan.assignment.data.source.network.model.LoginResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface AssignAppApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    companion object {

        @JvmStatic
        operator fun invoke(): AssignAppApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Gson parsing
                    .client(okHttpClient) // menghubungkan Retrofit ke OkHttp
                    .build()
            return retrofit.create(AssignAppApiService::class.java)
        }
    }
}