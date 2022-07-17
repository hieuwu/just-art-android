package com.hieuwu.justart.di

import com.hieuwu.justart.domain.repositories.SampleRepository
import com.hieuwu.justart.domain.repositories.impl.SampleRepositoryImpl
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.network.rertofit.ArtWorksServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ServiceModule {
    @Singleton
    @Binds
    abstract fun bindSampleRepository(impl: ArtWorksServiceImpl): ArtWorksService
}