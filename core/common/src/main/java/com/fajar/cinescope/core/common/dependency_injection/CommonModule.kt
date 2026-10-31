package com.fajar.cinescope.core.common.dependency_injection

import com.fajar.cinescope.core.common.DispatcherProvider
import com.fajar.cinescope.core.common.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object CommonModule {
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider =
        DispatcherProviderImpl
}