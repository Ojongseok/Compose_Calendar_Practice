package com.example.mildfistassignment.di

import com.example.mildfistassignment.model.CalendarDataSource
import com.example.mildfistassignment.model.CalendarUiModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCalendarDataSource(): CalendarDataSource {
        return CalendarDataSource()
    }

    @Provides
    @Singleton
    fun provideCalendarUiModel(calendarDataSource: CalendarDataSource): CalendarUiModel {
        return calendarDataSource.getData(lastSelectedDate = calendarDataSource.today)
    }
}