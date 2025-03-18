package com.recombee.android_app_demo.di

import com.recombee.apiclientkotlin.RecombeeClient
import com.recombee.apiclientkotlin.util.Region
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RecombeeModule {
    @Provides
    fun provideRecombeeClient(): RecombeeClient {
        return RecombeeClient(
            databaseId = "sample-organization-media-sample-db",
            publicToken = "ZVQBJERql0ctf93Vp8gQ0BdMAvwrEtMi6qzI2qrGcTUukkusdu4jn0TvFeiCp0bV",
            region = Region.EuWest,
        )
    }
}
