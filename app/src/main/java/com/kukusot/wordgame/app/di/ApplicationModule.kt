package com.kukusot.wordgame.app.di

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import com.google.gson.Gson
import com.kukusot.wordgame.sound.SoundManager
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

    @Provides
    @Reusable
    fun provideSoundManager(@ApplicationContext context: Context): SoundManager = SoundManager(
        context,
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager,
        SoundPool(1, AudioManager.STREAM_MUSIC, 0)
    )
}