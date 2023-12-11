package com.azrinurvani.mastercleanapparchitecture.di

import android.util.Log
import com.azrinurvani.mastercleanapparchitecture.BuildConfig
import com.azrinurvani.mastercleanapparchitecture.data.data_source.CoinGeckoApi
import com.azrinurvani.mastercleanapparchitecture.data.repository.CoinRepositoryImpl
import com.azrinurvani.mastercleanapparchitecture.domain.repository.CoinRepository
import com.azrinurvani.mastercleanapparchitecture.util.CALL_TIMEOUT
import com.azrinurvani.mastercleanapparchitecture.util.CONNECT_TIMEOUT
import com.azrinurvani.mastercleanapparchitecture.util.READ_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinGeckoModule {

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.d("CoinGecko-API", "log: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(
                interceptor
            ).connectTimeout(
                timeout = CONNECT_TIMEOUT,
                TimeUnit.SECONDS
            )
            .readTimeout(
                timeout = READ_TIMEOUT,
                TimeUnit.SECONDS
            )
            .callTimeout(
                timeout = CALL_TIMEOUT,
                TimeUnit.SECONDS
            )
            .build()

    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(httpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideConGeckoApi(retrofit: Retrofit) : CoinGeckoApi{
        return retrofit.create(CoinGeckoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinGeckoRepository(api : CoinGeckoApi) : CoinRepository{
        return CoinRepositoryImpl(api)
    }
}