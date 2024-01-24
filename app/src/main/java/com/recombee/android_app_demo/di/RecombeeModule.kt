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
            databaseId = "sample-organization-media-sample-db-v2",
            publicToken = "oc8saFASkgDyRImp5CIrnWO9pIU0STDglMOouLnS0YS40iWVgo61LflhKGSlYdvH",
            region = Region.EuWest,
            useHttpsByDefault = true,
        )
    }
}