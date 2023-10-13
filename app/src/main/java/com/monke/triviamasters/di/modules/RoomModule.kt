package com.monke.triviamasters.di.modules

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monke.triviamasters.data.room.database.TriviaDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun provideDatabase(context: Application): TriviaDatabase {
        val db = Room.databaseBuilder(
            context,
            TriviaDatabase::class.java, "trivia-database"
        ).build()
        return db
    }

    @Provides
    fun providePlayerDao(triviaDatabase: TriviaDatabase) = triviaDatabase.playerDao()

}