package com.example.makepizza_android.core

import android.util.Log
import com.example.makepizza_android.App
import com.example.makepizza_android.BuildConfig
import com.google.firebase.auth.FirebaseAuth
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
        val url = BuildConfig.MAKE_PIZZA_API_URL
        val converter = GsonConverterFactory.create()
        val client = this.getClient()

        return Retrofit.Builder().baseUrl(url).addConverterFactory(converter).client(client).build()
    }

    fun clearCache() {
        val client = this.getClient()

        try {
            client.cache()?.evictAll()
        } catch (ex: Exception) {
            Log.e("MakePizzaAPI", ex.message!!)
        }
    }

    private fun getClient(): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(App.context.cacheDir, cacheSize)
        val client = OkHttpClient.Builder().also {
            it.cache(cache)
            it.addInterceptor(ConnectionInterceptor())
            it.addInterceptor(AuthInterceptor())
            it.addNetworkInterceptor(CacheInterceptor())
            it.addNetworkInterceptor(UserCacheInterceptor())
            it.addNetworkInterceptor(ElementDetailsCacheInterceptor())
            it.connectTimeout(20L, TimeUnit.SECONDS)
            it.readTimeout(20L, TimeUnit.SECONDS)
            it.writeTimeout(20L, TimeUnit.SECONDS)
        }
        return client.build()
    }
}

class ConnectionInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: IOException) {
            throw IOException("Network Error", e)
        }
    }
}

class CacheInterceptor() : Interceptor {
    private val cacheableURLs = listOf(
        "/ingredients/fetch-all",
        "/ingredients/query",
        "/pizzas/fetch-all",
        "/pizzas/query"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().encodedPath().toString()
        val shouldCache = cacheableURLs.any { url.contains(it) }
        val response = chain.proceed(request)
        val serverCacheControl = response.header("Cache-Control")

        return if (serverCacheControl?.contains("no-cache") == true || !shouldCache) {
            response
        } else {
            response.newBuilder().also {
                it.header("Cache-Control", "public, max-age=${120 * 60}")
            }.build()
        }
    }
}

class UserCacheInterceptor(): Interceptor {
    private val cacheableURLs = listOf(
        "/users/current",
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().encodedPath().toString()
        val shouldCache = cacheableURLs.any { url.contains(it) }
        val response = chain.proceed(request)
        val serverCacheControl = response.header("Cache-Control")

        return if (serverCacheControl?.contains("no-cache") == true || !shouldCache) {
            response
        } else {
            response.newBuilder().also {
                it.header("Cache-Control", "public, max-age=${5 * 60}")
            }.build()
        }
    }
}

class ElementDetailsCacheInterceptor(): Interceptor {
    private val cacheableURLs = listOf(
        "/pizzas",
        "/ingredients",
        "/pizzas/user-defined"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().encodedPath().toString()
        Log.d("Retrofit", url)
        val shouldCache = cacheableURLs.any { url.endsWith(it) }
        val response = chain.proceed(request)
        val serverCacheControl = response.header("Cache-Control")

        return if (serverCacheControl?.contains("no-cache") == true || !shouldCache) {
            response
        } else {
            response.newBuilder().also {
                it.header("Cache-Control", "public, max-age=${2 * 60}")
            }.build()
        }
    }
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.result?.token
        val newRequest = if (token != null) {
            originalRequest.newBuilder().also {
                it.header("Authorization", "Bearer $token")
            }.build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}
