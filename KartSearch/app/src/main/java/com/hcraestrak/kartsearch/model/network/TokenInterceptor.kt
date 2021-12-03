package com.hcraestrak.kartsearch.model.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization", PreferenceUtils.getToken()!!).build()
        return chain.proceed(request)
    }
}