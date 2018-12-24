package com.devgeek.footballmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavouriteTeamOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: FavouriteTeamOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavouriteTeamOpenHelper {
            if (instance == null) {
                instance = FavouriteTeamOpenHelper(ctx.applicationContext)
            }
            return instance as FavouriteTeamOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavouriteTeam.TABLE_FAVOURITE_TEAM, true,
                FavouriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavouriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavouriteTeam.TEAM_NAME to TEXT,
                FavouriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavouriteTeam.TABLE_FAVOURITE_TEAM, true)
    }
}

// Access property for Context
val Context.databaseTeam: FavouriteTeamOpenHelper
    get() = FavouriteTeamOpenHelper.getInstance(applicationContext)