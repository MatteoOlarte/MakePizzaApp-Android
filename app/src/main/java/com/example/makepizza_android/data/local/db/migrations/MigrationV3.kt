package com.example.makepizza_android.data.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationV3 : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP INDEX IF EXISTS ix_ShoppingCart_user_id")

        db.execSQL("DROP INDEX IF EXISTS ix_ShoppingCart_item_id")

        db.execSQL("DROP TABLE IF EXISTS ShoppingCart")

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS ShoppingCart (
                cart_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                user_id TEXT NOT NULL,
                item_id TEXT NOT NULL,
                FOREIGN KEY(user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                FOREIGN KEY(item_id) REFERENCES CartItem(item_id) ON DELETE CASCADE
            )
        """.trimIndent())

        db.execSQL("""
            CREATE UNIQUE INDEX ix_unq_ShoppingCart_UserID_ItemID 
            ON ShoppingCart(user_id, item_id)
        """.trimIndent())
    }
}