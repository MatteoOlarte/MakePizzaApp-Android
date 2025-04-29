package com.example.makepizza_android.core

import com.example.makepizza_android.App
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object MakePizzaAPI {
    fun getRetrofit(): Retrofit {
        val url = "http://192.168.1.75:8000"
        val converter = GsonConverterFactory.create()
        val client = this.getClient()

        return Retrofit.Builder().baseUrl(url).addConverterFactory(converter).client(client).build()
    }

    private fun getClient(): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(App.context.cacheDir, cacheSize)
        val client = OkHttpClient.Builder().also {
            it.cache(cache)
            it.addInterceptor(ConnectionInterceptor())
            it.addNetworkInterceptor(CacheInterceptor())
            it.connectTimeout(20L, TimeUnit.SECONDS)
            it.readTimeout(20L, TimeUnit.SECONDS)
            it.writeTimeout(20L, TimeUnit.SECONDS)
        }
        return client.build()
    }
}

class ConnectionInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: IOException) {
            throw IOException("Network Error", e)
        }
    }
}

class CacheInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val shouldCache = url.contains("fetch-all")
        val response = chain.proceed(request)

        return if (shouldCache) {
            response.newBuilder().also {
                it.header("Cache-Control", "public, max-age=${15 * 60}")
            }.build()
        } else {
            response
        }
    }
}
