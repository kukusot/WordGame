package com.kukusot.wordgame.app.di

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class ApplicationModule {

    @Provides
    fun provideAssets(@ApplicationContext context: Context): AssetManager = context.assets

    @Reusable
    @Provides
    fun provideGson(): Gson = Gson()
}