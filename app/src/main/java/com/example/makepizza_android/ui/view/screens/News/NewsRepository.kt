package com.example.makepizza_android.domain.repositories

import com.example.makepizza_android.domain.models.NewsArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class NewsRepository {

    suspend fun fetchGastronomyNews(): List<NewsArticle> = withContext(Dispatchers.IO) {
        val apiKey = "4f3eee9e891dd1cffb8db4063afa659b"
        val urlStr = "https://gnews.io/api/v4/search?" +
                "q=(gastronomía OR restaurante OR \"comida gourmet\")&" +
                "lang=es&" +
                "country=es,mx,ar&" +
                "sortby=publishedAt&" +
                "max=10&" +
                "token=$apiKey"

        val url = URL(urlStr)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 15000
        connection.readTimeout = 10000

        try {
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("Error: ${connection.responseCode}")
            }

            val response = connection.inputStream.bufferedReader().readText()
            val json = JSONObject(response)
            val articlesJson = json.getJSONArray("articles")
            val articles = mutableListOf<NewsArticle>()

            for (i in 0 until articlesJson.length()) {
                val item = articlesJson.getJSONObject(i)
                val source = item.getJSONObject("source")

                articles.add(
                    NewsArticle(
                        title = item.getString("title"),
                        description = item.optString("description").takeIf { it != "null" && it.isNotEmpty() },
                        url = item.getString("url"),
                        image = item.optString("image").takeIf { it != "null" && it.isNotEmpty() },
                        publishedAt = formatApiDate(item.getString("publishedAt")),
                        sourceName = source.getString("name")
                    )
                )
            }

            return@withContext articles.filter { it.description != null }
        } finally {
            connection.disconnect()
        }
    }

    private fun formatApiDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
            val date = inputFormat.parse(isoDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            isoDate
        }
    }
}