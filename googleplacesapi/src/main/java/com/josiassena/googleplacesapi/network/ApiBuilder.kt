package com.josiassena.googleplacesapi.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Josias Sena
 */
class ApiBuilder {

    companion object {
        private const val DEFAULT_HOST = "https://maps.googleapis.com/"
    }

    fun buildApi(): GooglePlacesApi = getRetrofitInstance(DEFAULT_HOST).create(GooglePlacesApi::class.java)

    private fun getRetrofitInstance(host: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(getGSONConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build()
    }

    private fun getGSONConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create()

        return GsonConverterFactory.create(gson)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }
}