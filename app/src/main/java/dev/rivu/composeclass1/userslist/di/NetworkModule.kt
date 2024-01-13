package dev.rivu.composeclass1.userslist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rivu.composeclass1.userslist.data.remote.RetrofitFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideRetrofit() = RetrofitFactory.getRetrofit("https://api.slingacademy.com/v1/")
}