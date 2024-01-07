package com.hocheol.respal.di

import android.content.Context
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.api.RespalApi
import com.hocheol.respal.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMainRepository(respalApi: RespalApi) = MainRepository(respalApi)

    @Provides
    @Singleton
    fun provideSharedPreferenceStorage(@ApplicationContext context: Context): SharedPreferenceStorage {
        return SharedPreferenceStorage(context)
    }
}