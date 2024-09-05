package com.lloydsbyte.smarttasks.utils.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

interface NetworkClient {
    companion object {

        private fun buildClient(): OkHttpClient.Builder {
            return OkHttpClient.Builder().apply {
                connectTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
            }
        }

        fun client(baseUrl: String) : NetworkClient {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildClient().build())
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(NetworkClient::class.java)
        }
    }

    @Headers("Content-Type: application/json")
    @GET(NetworkConstants.endpointUrl)
    suspend fun getTaskList(): Response<TaskResponse.MainResponse>


}