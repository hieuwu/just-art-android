package com.hieuwu.justart.di

import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.data.repository.impl.ArtworkRepositoryImpl
import com.hieuwu.justart.domain.repositories.SampleRepository
import com.hieuwu.justart.domain.repositories.impl.SampleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSampleRepository(impl: SampleRepositoryImpl): SampleRepository

    @Singleton
    @Binds
    abstract fun bindArtworkRepository(impl: ArtworkRepositoryImpl): ArtworkRepository
}