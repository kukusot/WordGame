package com.kukusot.wordgame.di

import com.kukusot.wordgame.data.WordsDataSource
import com.kukusot.wordgame.data.WordsLocalDataSource
import com.kukusot.wordgame.domain.WordsRepository
import com.kukusot.wordgame.domain.WordsRepositoryImpl
import com.kukusot.wordgame.usecase.GameManager
import com.kukusot.wordgame.usecase.GameManagerImpl
import com.kukusot.wordgame.usecase.QuestionFactory
import com.kukusot.wordgame.usecase.QuestionFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class GameModule {

    @Binds
    abstract fun bindWordsDataSource(dataSource: WordsLocalDataSource): WordsDataSource

    @Binds
    abstract fun bindWordsRepository(repositoryImpl: WordsRepositoryImpl): WordsRepository

    @Binds
    abstract fun bindQuestionFactory(factory: QuestionFactoryImpl): QuestionFactory

    @Binds
    abstract fun bindGameManager(gameManager: GameManagerImpl): GameManager
}