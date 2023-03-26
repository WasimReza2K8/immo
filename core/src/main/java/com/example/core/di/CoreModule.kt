package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.BuildConfig.BASE_URL
import com.example.core.dispatcher.BaseDispatcherProvider
import com.example.core.dispatcher.MainDispatcherProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.leakcanary.core.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreModule {
    @Singleton
    @Binds
    fun provideDispatcher(dispatcherProvider: MainDispatcherProvider): BaseDispatcherProvider

    companion object {
        private const val TIME_OUT = 30L
        private val json = Json {
            ignoreUnknownKeys = true
        }
        private val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        private val apiInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder().build()
            request.url(url)
            chain.proceed(request.build())
        }

        @Provides
        @Singleton
        fun providesOkHttpClient(): OkHttpClient {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(apiInterceptor)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                okHttpClient.addInterceptor(loggingInterceptor)
            }
            return okHttpClient.build()
        }

        @Singleton
        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
        ): Retrofit {
            val contentType = "application/json".toMediaType()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(
                    json.asConverterFactory(contentType)
                )
                .build()
        }
    }
}
