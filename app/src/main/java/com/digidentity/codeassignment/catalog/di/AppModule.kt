package com.digidentity.codeassignment.catalog.di

import com.digidentity.codeassignment.catalog.data.network.CatalogApiService
import com.digidentity.codeassignment.catalog.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.BASE_URL).build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { interpolator ->
            val request = interpolator.request()
            val newRequest =
                request.newBuilder().addHeader(Constants.HEADER_NAME, Constants.HEADER_VALUE)
                    .build()
            interpolator.proceed(newRequest)
        }.build()

    @Singleton
    @Provides
    fun provideCatalogNetApi(retrofit: Retrofit): CatalogApiService =
        retrofit.create(CatalogApiService::class.java)



}
