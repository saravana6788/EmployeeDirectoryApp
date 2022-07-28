package com.block.employeedirectoryapp.di

import com.block.employeedirectoryapp.BuildConfig
import com.block.employeedirectoryapp.api.EmployeeAPIService
import com.block.employeedirectoryapp.data.AppDispatchers
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providesMoshi():Moshi{
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun providesEmployeeApiService(retrofit:Retrofit):EmployeeAPIService{
        return retrofit.create(EmployeeAPIService::class.java)
    }

    @Provides
    fun providesAppDispatchers(): AppDispatchers {
        return AppDispatchers()
    }




}