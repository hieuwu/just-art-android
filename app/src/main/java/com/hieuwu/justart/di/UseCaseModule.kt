package com.hieuwu.justart.di

import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justart.domain.usecases.SampleUseCase
import com.hieuwu.justart.domain.usecases.impl.RetrieveArtWorksUseCaseImpl
import com.hieuwu.justart.domain.usecases.impl.SampleUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindSampleUseCase(impl: SampleUseCaseImpl): SampleUseCase

    @Singleton
    @Binds
    abstract fun bindRetrieveArtWorksUseCase(impl: RetrieveArtWorksUseCaseImpl): RetrieveArtWorksUseCase

}