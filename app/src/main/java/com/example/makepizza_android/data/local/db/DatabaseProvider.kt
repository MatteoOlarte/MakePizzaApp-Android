package com.example.makepizza_android.data.local.db

import android.content.Context
import androidx.room.Room
import com.example.makepizza_android.data.local.db.migrations.MigrationV2
import com.example.makepizza_android.data.local.db.migrations.MigrationV3

object DatabaseProvider {
    @Volatile
    private var INSTANCE: ApplicationDataBase? = null

    fun getDatabase(context: Context): ApplicationDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context = context.applicationContext,
                klass = ApplicationDataBase::class.java,
                name = "database.db"
            ).also {
                it.addMigrations(MigrationV2())
                it.addMigrations(MigrationV3())
            }.build()
            INSTANCE = instance
            instance
        }
    }
}