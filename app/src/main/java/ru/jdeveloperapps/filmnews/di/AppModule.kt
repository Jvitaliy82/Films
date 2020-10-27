package ru.jdeveloperapps.filmnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.jdeveloperapps.filmnews.repositories.MostPopularTVShowRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository() = MostPopularTVShowRepository()

}