package com.digidentity.codeassignment.catalog.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.security.crypto.MasterKeys
import com.digidentity.codeassignment.catalog.data.database.ItemsDao
import com.digidentity.codeassignment.catalog.data.database.ItemsDataBase
import com.digidentity.codeassignment.catalog.data.network.CatalogApiService
import com.digidentity.codeassignment.catalog.data.repository.CatalogRepositoryImpl
import com.digidentity.codeassignment.catalog.domain.repository.CatalogRepository
import com.digidentity.codeassignment.catalog.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.BASE_URL).build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { interpolator ->
            val request = interpolator.request()
            val newRequest =
                request.newBuilder()
                    .header(Constants.CONTENT_TYPE_NAME, Constants.CONTENT_TYPE_VALUE)
                    .addHeader(Constants.HEADER_NAME, Constants.HEADER_VALUE)
                    .build()
            interpolator.proceed(newRequest)
        }.build()

    @Singleton
    @Provides
    fun provideCatalogRepository(apiService: CatalogApiService, dao: ItemsDao): CatalogRepository =
        CatalogRepositoryImpl(apiService, dao)

    @Singleton
    @Provides
    fun provideCatalogNetApi(retrofit: Retrofit): CatalogApiService =
        retrofit.create(CatalogApiService::class.java)


    @Named(Constants.IO_DISPATCHER)
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Named(Constants.MAIN_DISPATCHER)
    @Singleton
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main


    @Singleton
    @Provides
    fun provideItemsDatabase(
        @ApplicationContext context: Context,
        @Named(Constants.MASTER_KEY) masterKey: String
    ): ItemsDataBase =
        Room.databaseBuilder(context, ItemsDataBase::class.java, Constants.DATABASE_NAME)
            .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes(masterKey.toCharArray())))
            .build()


    @Singleton
    @Provides
    fun provideItemsDao(dataBase: ItemsDataBase): ItemsDao = dataBase.getDao()


    @Named(Constants.MASTER_KEY)
    @Singleton
    @Provides
    fun provideMasterKey(): String =
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

}
