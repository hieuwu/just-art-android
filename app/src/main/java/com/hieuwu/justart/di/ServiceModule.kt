package com.hieuwu.justart.di

import com.hieuwu.justartsdk.ServiceConfigurationProvider
import com.hieuwu.justartsdk.articles.v1.ArticlesService
import com.hieuwu.justartsdk.articles.v1.ArticlesServiceConfigProvider
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.ArtWorksServiceConfigProvider
import com.hieuwu.justartsdk.events.v1.EventsService
import com.hieuwu.justartsdk.events.v1.EventsServiceConfigProvider
import com.hieuwu.justartsdk.exhibitions.v1.ExhibitionsService
import com.hieuwu.justartsdk.exhibitions.v1.ExhibitionsServiceConfigProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideArtWorksService(serviceConfiguration: ServiceConfigurationProvider.ServiceConfiguration):
            ArtWorksService {
        val accountConfig = ArtWorksServiceConfigProvider.ArtWorksConfiguration.Builder()
            .serviceConfiguration(serviceConfiguration)
            .build()
        return ArtWorksServiceConfigProvider(accountConfig)
            .artWorksServiceBuilder().build()
    }

    @Provides
    @Singleton
    fun provideExhibitionsService(serviceConfiguration: ServiceConfigurationProvider.ServiceConfiguration):
            ExhibitionsService {
        val config = ExhibitionsServiceConfigProvider.ExhibitionConfiguration.Builder()
            .serviceConfiguration(serviceConfiguration)
            .build()
        return ExhibitionsServiceConfigProvider(config)
            .exhibitionsServiceBuilder().build()
    }

    @Provides
    @Singleton
    fun provideArticlesService(serviceConfiguration: ServiceConfigurationProvider.ServiceConfiguration):
            ArticlesService {
        val config = ArticlesServiceConfigProvider.ArticlesConfiguration.Builder()
            .serviceConfiguration(serviceConfiguration)
            .build()
        return ArticlesServiceConfigProvider(config).articlesServiceBuilder().build()
    }

    @Provides
    @Singleton
    fun provideEventsService(serviceConfiguration: ServiceConfigurationProvider.ServiceConfiguration):
            EventsService {
        val config = EventsServiceConfigProvider.EventsConfiguration.Builder()
            .serviceConfiguration(serviceConfiguration)
            .build()
        return EventsServiceConfigProvider(config)
            .eventsServiceBuilder().build()
    }

    @Provides
    @Singleton
    fun provideServiceConfiguration(connectionConfiguration: ServiceConfigurationProvider.ConnectionConfiguration) =
        getServiceConfigurationBuilder(connectionConfiguration).build()

    @Provides
    @Singleton
    fun provideConnectionConfiguration() =
        getConnectionConfigurationBuilder().build()

    private fun getConnectionConfigurationBuilder() =
        ServiceConfigurationProvider.ConnectionConfiguration.Builder()
            .apiBaseUrl("https://api.artic.edu/api/")
            .shouldRetry(true)

    private fun getServiceConfigurationBuilder(
        connectionConfiguration:
        ServiceConfigurationProvider.ConnectionConfiguration
    ) = ServiceConfigurationProvider.ServiceConfiguration.Builder()
        .connectionConfiguration(connectionConfiguration)
        .logEnabled(true)
}