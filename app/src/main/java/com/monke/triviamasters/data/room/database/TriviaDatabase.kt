package com.monke.triviamasters.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.monke.triviamasters.data.room.dao.PlayerDao
import com.monke.triviamasters.data.room.model.PlayerRoom

@Database(entities = [PlayerRoom::class], version = 1)
abstract class TriviaDatabase: RoomDatabase() {

    abstract fun playerDao(): PlayerDao

}