package com.kukusot.wordgame.di

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.kukusot.wordgame.data.WordsDataSource
import com.kukusot.wordgame.data.WordsLocalDataSource
import com.kukusot.wordgame.domain.WordsRepository
import com.kukusot.wordgame.domain.WordsRepositoryImpl
import com.kukusot.wordgame.scores.HighScoresRepository
import com.kukusot.wordgame.scores.PrefsHighScoreRepository
import com.kukusot.wordgame.usecase.GameManager
import com.kukusot.wordgame.usecase.GameManagerImpl
import com.kukusot.wordgame.usecase.QuestionFactoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
class GameModule {

    @Provides
    fun provideWordsDataSource(assetManager: AssetManager, gson: Gson): WordsDataSource =
        WordsLocalDataSource(assetManager, gson)

    @Provides
    fun provideWordsRepository(wordsDataSource: WordsDataSource): WordsRepository =
        WordsRepositoryImpl(wordsDataSource)

    @Provides
    fun provideGameManager(
        repository: WordsRepository
    ): GameManager = GameManagerImpl(QuestionFactoryImpl(), repository)

    @Provides
    fun provideHighScoreRepository(@ApplicationContext context: Context): HighScoresRepository {
        val prefs = context.getSharedPreferences("scores", Context.MODE_PRIVATE)
        return PrefsHighScoreRepository(prefs)
    }
}