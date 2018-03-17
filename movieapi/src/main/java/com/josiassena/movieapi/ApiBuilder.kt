package com.josiassena.movieapi

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Josias Sena
 */
class ApiBuilder(private val context: Context) {

    companion object {
        const val DEFAULT_HOST = "https://api.themoviedb.org/3/"
    }

    fun buildApi(host: String): Api = getRetrofitInstance(context, host).create(Api::class.java)

    private fun getRetrofitInstance(context: Context, host: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(getGSONConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(context))
                .build()
    }

    private fun getGSONConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create())
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(getCache(context))
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
    }

    private fun getCache(context: Context) = Cache(context.cacheDir, 12 * 1024 * 102)
}