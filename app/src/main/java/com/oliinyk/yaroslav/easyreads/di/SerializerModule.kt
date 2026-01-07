package com.oliinyk.yaroslav.easyreads.di

import com.oliinyk.yaroslav.easyreads.domain.serializer.LocalDateTimeSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializerModule {
    @Provides
    @Singleton
    fun providesJson(): Json =
        Json {
            prettyPrint = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            serializersModule =
                SerializersModule {
                    contextual(LocalDateTime::class, LocalDateTimeSerializer)
                }
        }
}
