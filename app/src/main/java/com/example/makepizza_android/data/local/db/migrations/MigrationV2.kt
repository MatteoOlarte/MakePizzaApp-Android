package com.example.makepizza_android.data.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationV2 : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS CartItem (
                item_id TEXT NOT NULL,
                name TEXT NOT NULL,
                desc TEXT,
                price REAL NOT NULL,
                size TEXT NOT NULL,
                cached_at TEXT NOT NULL,
                PRIMARY KEY(item_id)
            );
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS ShoppingCart (
                user_id TEXT NOT NULL,
                item_id TEXT NOT NULL,
                PRIMARY KEY(user_id, item_id),
                FOREIGN KEY(user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                FOREIGN KEY(item_id) REFERENCES CartItem(item_id) ON DELETE CASCADE
            );
            """.trimIndent()
        )

        db.execSQL("CREATE INDEX ix_Users_active_address ON Users(active_address)")
        db.execSQL("CREATE INDEX ix_ShoppingCart_user_id ON ShoppingCart(user_id)")
        db.execSQL("CREATE INDEX ix_ShoppingCart_item_id ON ShoppingCart(item_id)")
    }
}
