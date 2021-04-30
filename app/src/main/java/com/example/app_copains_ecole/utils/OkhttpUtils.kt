package com.example.app_copains_ecole.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

val client = OkHttpClient()
val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()


fun sendGetOkHttpRequest(url: String): String {
    println("url : $url")
    val request = Request.Builder().url(url).build()
    val response = client.newCall(request).execute()
    return if (response.code !in 200..299) {
        throw Exception("Réponse du serveur incorrect : ${response.code}")
    }
    else {
        response.body?.string() ?: ""
    }
}


fun sendPostOkHttpRequest(url: String, paramJson: String): String {
    println("url : $url")
    val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)
    val request = Request.Builder().url(url).post(body).build()
    val response = client.newCall(request).execute()

    return if (response.code !in 200..299) {
        throw Exception("Réponse du serveur incorrect :  ${response.code}")
    }
    else {
        response.body?.string() ?: ""
    }
}