package com.devgeek.footballmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavouriteMatchOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavouriteMatch.db", null, 1) {
    companion object {
        private var instance: FavouriteMatchOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavouriteMatchOpenHelper {
            if (instance == null) {
                instance = FavouriteMatchOpenHelper(ctx.applicationContext)
            }
            return instance as FavouriteMatchOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavouriteMatch.TABLE_FAVOURITE_MATCH, true,
                FavouriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavouriteMatch.EVENT_ID to TEXT + UNIQUE,
                FavouriteMatch.EVENT_NAME to TEXT,
                FavouriteMatch.EVENT_HOME_TEAM to TEXT,
                FavouriteMatch.EVENT_HOME_SCORE to TEXT,
                FavouriteMatch.EVENT_AWAY_TEAM to TEXT,
                FavouriteMatch.EVENT_AWAY_SCORE to TEXT,
                FavouriteMatch.EVENT_DATE to TEXT,
                FavouriteMatch.EVENT_TIME to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavouriteMatch.TABLE_FAVOURITE_MATCH, true)
    }
}

// Access property for Context
val Context.databaseMatch: FavouriteMatchOpenHelper
    get() = FavouriteMatchOpenHelper.getInstance(applicationContext)