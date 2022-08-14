package com.hieuwu.justart.di

import com.hieuwu.justartsdk.ServiceConfigurationProvider
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.ArtWorksServiceConfigProvider
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