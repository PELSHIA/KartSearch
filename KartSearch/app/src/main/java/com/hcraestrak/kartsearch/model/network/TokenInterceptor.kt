package com.hcraestrak.kartsearch.model.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {

    private val Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTQwOTgzMTY5NiIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTM5MyIsIlgtQXBwLVJhdGUtTGltaXQiOiI1MDA6MTAiLCJuYmYiOjE2MjMxMTA1MjEsImV4cCI6MTYzODY2MjUyMSwiaWF0IjoxNjIzMTEwNTIxfQ.K9ZXPHbpyTbbuIb5oVecXlKZkbsrjSJdO0ArBJvp6_I"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization", Token).build()
        return chain.proceed(request)
    }
}