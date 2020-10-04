package com.kukusot.wordgame.di

import android.content.res.AssetManager
import com.google.gson.Gson
import com.kukusot.wordgame.data.WordsDataSource
import com.kukusot.wordgame.data.WordsLocalDataSource
import com.kukusot.wordgame.domain.WordsRepository
import com.kukusot.wordgame.domain.WordsRepositoryImpl
import com.kukusot.wordgame.usecase.GameManager
import com.kukusot.wordgame.usecase.GameManagerImpl
import com.kukusot.wordgame.usecase.QuestionFactory
import com.kukusot.wordgame.usecase.QuestionFactoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class GameModule {

    @Provides
    fun provideWordsDataSource(assetManager: AssetManager, gson: Gson): WordsDataSource =
        WordsLocalDataSource(assetManager, gson)

    @Provides
    fun provideWordsRepository(wordsDataSource: WordsDataSource): WordsRepository =
        WordsRepositoryImpl(wordsDataSource)

    @Provides
    fun provideQuestionFactory(wordsRepository: WordsRepository): QuestionFactory =
        QuestionFactoryImpl(wordsRepository)

    @Provides
    fun provideGameManager(
        repository: WordsRepository,
        questionFactory: QuestionFactory
    ): GameManager = GameManagerImpl(questionFactory, repository)
}