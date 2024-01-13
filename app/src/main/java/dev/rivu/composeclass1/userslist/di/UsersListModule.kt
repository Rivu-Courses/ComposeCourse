package dev.rivu.composeclass1.userslist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.rivu.composeclass1.userslist.data.remote.UsersService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class UsersListModule {
    @Provides
    fun getUsersListService(retrofit: Retrofit) = retrofit.create(UsersService::class.java)
}