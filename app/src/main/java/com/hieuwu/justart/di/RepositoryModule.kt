package com.hieuwu.justart.di

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
}