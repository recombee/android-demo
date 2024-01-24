package com.recombee.android_app_demo.settings

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.google.protobuf.InvalidProtocolBufferException
import com.recombee.android_app_demo.UserSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import javax.inject.Singleton

object UserSettingsSerializer : Serializer<UserSettings> {
    override val defaultValue: UserSettings = UserSettings.getDefaultInstance()
        .toBuilder()
        .setUserId(UUID.randomUUID().toString())
        .setOnboardingShown(false)
        .build()

    override suspend fun readFrom(input: InputStream): UserSettings {
        try {
            return UserSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) = t.writeTo(output)
}

private const val USER_SETTINGS_NAME = "user_settings"
private const val USER_SETTINGS_FILE = "$USER_SETTINGS_NAME.pb"

@Module
@InstallIn(SingletonComponent::class)
object UserSettingsModule {
    @Singleton
    @Provides
    fun provideUserSettings(
        @ApplicationContext context: Context,
    ): DataStore<UserSettings> {
        return DataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler {
                UserSettingsSerializer.defaultValue
            },
            migrations = listOf(),
            serializer = UserSettingsSerializer,
            produceFile = { context.dataStoreFile(USER_SETTINGS_FILE) },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
