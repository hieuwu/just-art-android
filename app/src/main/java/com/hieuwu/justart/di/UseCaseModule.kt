package com.hieuwu.justart.di

import com.hieuwu.justart.domain.usecases.*
import com.hieuwu.justart.domain.usecases.impl.*
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

    @Singleton
    @Binds
    abstract fun bindRetrieveArtWorkDetailsUseCase(impl: RetrieveArtWorkDetailsUseCaseImpl): RetrieveArtWorkDetailsUseCase

    @Singleton
    @Binds
    abstract fun bindSearchArtWorkDetailsUseCase(impl: SearchArtWorkUseCaseImpl): SearchArtWorkUseCase

    @Singleton
    @Binds
    abstract fun bindSaveFavoriteArtWorkUseCase(impl: SaveFavoriteArtWorkUseCaseImpl): SaveFavoriteArtWorkUseCase

    @Singleton
    @Binds
    abstract fun bindCheckFavoriteArtWorkExistedUseCase(impl: CheckFavoriteArtWorkExistedUseCaseImpl): CheckFavoriteArtWorkExistedUseCase

    @Singleton
    @Binds
    abstract fun bindDeleteFavoriteArtWorkUseCase(impl: DeleteFavoriteArtWorkUseCaseImpl): DeleteFavoriteArtWorkUseCase

    @Singleton
    @Binds
    abstract fun bindGetFavoriteArtWorkUseCase(impl: GetFavoriteArtWorkUseCaseImpl): GetFavoriteArtWorkUseCase

    @Singleton
    @Binds
    abstract fun bindRetrieveExhibitionsUseCase(impl: RetrieveExhibitionsUseCaseImpl): RetrieveExhibitionsUseCase


    @Singleton
    @Binds
    abstract fun bindGetEventsUseCase(impl: GetEventsUseCaseImpl): GetEventsUseCase

}