package com.example.vds.remote

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun <T> ApolloCall<T>.execute() = suspendCoroutine<com.apollographql.apollo.api.Response<T>> { cont ->
    enqueue(object: ApolloCall.Callback<T>() {
        override fun onResponse(response: com.apollographql.apollo.api.Response<T>) {
            cont.resume(response)
        }
        override fun onFailure(e: ApolloException) {
            cont.resumeWithException(e)
        }
    })
}

object GraphQLClient {
    private val TIME_OUT: Long = 5000
    private val YELP_API = "https://api.yelp.com/v3/graphql"
    private var okHttpClient: OkHttpClient? = null


    // get the instance of apollo client with all the headers and correct url
    fun getApolloClient(): ApolloClient {

        if (okHttpClient == null) {
            okHttpClient = getOkHttpClient(API_KEY)
        }

        return ApolloClient.builder()
                .serverUrl(YELP_API)
                .okHttpClient(okHttpClient!!)
                .build()
    }

    private fun getOkHttpClient(authToken: String): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // set the timeouts
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)

        addLoggingInterceptor(builder)
        builder.addInterceptor(RequestInterceptor(authToken))

        return builder.build()
    }

    private fun addLoggingInterceptor(builder: OkHttpClient.Builder) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)
    }

    private class RequestInterceptor(private val authToken: String) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val requestBuilder = request.newBuilder()
            requestBuilder.addHeader("Authorization", "Bearer $authToken")
            return chain.proceed(requestBuilder.build())
        }
    }
    //make this a String resource or inject it using DI
    private const val API_KEY = "5IsJhM5sj0Vdj19PlO7smTGymBFRHa6mCiM2gJ8IAByDPxCnGUff11jUB9bkxB7fzFzwPfv-Gb0HciHSQcs2rl1T5Vy8mjRe_LyZfTppAxwXiCamw-hE6YiWc9ibW3Yx"
}