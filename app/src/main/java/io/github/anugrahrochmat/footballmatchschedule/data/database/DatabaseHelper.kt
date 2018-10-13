package io.github.anugrahrochmat.footballmatchschedule.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "FavouriteMatch.db", null, 4) {

    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Favourite.TABLE_FAVOURITE, true,
                Favourite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favourite.MATCH_ID to TEXT + UNIQUE,
                Favourite.HOME_TEAM_NAME to TEXT,
                Favourite.HOME_TEAM_SCORE to TEXT,
                Favourite.HOME_TEAM_BADGE to TEXT,
                Favourite.AWAY_TEAM_NAME to TEXT,
                Favourite.AWAY_TEAM_SCORE to TEXT,
                Favourite.AWAY_TEAM_BADGE to TEXT,
                Favourite.DATE_EVENT to TEXT,
                Favourite.STR_TIME to TEXT)

        db.createTable(TeamFavourite.TABLE_TEAM_FAVOURITE, true,
                TeamFavourite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamFavourite.TEAM_ID to TEXT + UNIQUE,
                TeamFavourite.TEAM_NAME to TEXT,
                TeamFavourite.TEAM_BADGE to TEXT,
                TeamFavourite.TEAM_STADIUM to TEXT,
                TeamFavourite.TEAM_DESCRIPTION to TEXT,
                TeamFavourite.TEAM_FORMED_YEAR to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favourite.TABLE_FAVOURITE, true)
        db.dropTable(TeamFavourite.TABLE_TEAM_FAVOURITE, true)
    }

    fun clearTable() {
        writableDatabase.execSQL("DELETE FROM ${Favourite.TABLE_FAVOURITE}")
        writableDatabase.execSQL("DELETE FROM ${TeamFavourite.TABLE_TEAM_FAVOURITE}")
    }
}

// Access property for Context
val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)