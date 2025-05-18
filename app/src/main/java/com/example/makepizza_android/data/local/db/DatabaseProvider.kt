package com.example.makepizza_android.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: ApplicationDataBase? = null

    fun getDatabase(context: Context): ApplicationDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context = context.applicationContext,
                klass = ApplicationDataBase::class.java,
                name = "database.db"
            ).addMigrations(MigrationV2()).build()
            INSTANCE = instance
            instance
        }
    }
}