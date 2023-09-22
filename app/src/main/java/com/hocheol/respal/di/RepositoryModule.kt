package com.hocheol.respal.di

import com.hocheol.respal.data.remote.api.SampleApi
import com.hocheol.respal.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        sampleApi: SampleApi
    ) = MainRepository(sampleApi)
}