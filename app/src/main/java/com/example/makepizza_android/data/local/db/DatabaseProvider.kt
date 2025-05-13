package com.example.makepizza_android.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: ApplicationDataBase? = null

    fun getDatabase(context: Context): ApplicationDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ApplicationDataBase::class.java,
                "database.db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}