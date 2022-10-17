package com.hcraestrak.data.remote

import com.hcraestrak.data.remote.PreferenceUtils.token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization", token?:"").build()
        return chain.proceed(request)
    }
}