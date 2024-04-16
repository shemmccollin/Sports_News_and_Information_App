package com.example.sportsnewsandinformationapp.modals

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val sportsQuery = ("CREATE TABLE " + TABLE_SPORTS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                SPORTSTYPE_COL + " TEXT," +
                SPORTSNAME_COL + " TEXT," +
                INSTRUCTION_COL + " TEXT" +")")

        val newsQuery = ("CREATE TABLE " + TABLE_NEWS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NEWS_IMAGE_URL_COL + " TEXT," +
                NEWS_TITLE_COL + " TEXT," +
                NEWS_DESCRIPTION_COL + " TEXT" +")")

        val athletesQuery = ("CREATE TABLE " + TABLE_ATHLETES + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                ATHLETES_NAME_COL + " TEXT," +
                ATHLETES_SPORT_NAME_COL + " TEXT," +
                ATHLETES_COUNTRY_COL + " TEXT," +
                ATHLETES_BEST_PERFORMANCE_COL + " TEXT," +
                ATHLETES_MEDALS_COL + " TEXT," +
                ATHLETES_FACTS_COL + " TEXT" +")")

        val eventsQuery = ("CREATE TABLE " + TABLE_EVENTS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                EVENTS_NAME_COL + " TEXT," +
                EVENTS_DATE_COL + " TEXT," +
                EVENTS_DESCRIPTION_COL + " TEXT" +")")

        val archiveQuery = ("CREATE TABLE " + TABLE_ARCHIVE + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                ARCHIVE_NAME_COL + " TEXT," +
                ARCHIVE_DATE_COL + " TEXT," +
                ARCHIVE_DESCRIPTION_COL + " TEXT" +")")

        val usersQuery = ("CREATE TABLE " + TABLE_USERS + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                USERNAME_COL + " TEXT," +
                PASSWORD_COL + " TEXT" + ")")

        db.execSQL(sportsQuery)
        db.execSQL(newsQuery)
        db.execSQL(athletesQuery)
        db.execSQL(eventsQuery)
        db.execSQL(archiveQuery)
        db.execSQL(usersQuery)
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORTS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATHLETES)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARCHIVE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)
    }

    fun addUser(username: String, password: String){

        val values = ContentValues()

        values.put(USERNAME_COL, username)
        values.put(PASSWORD_COL, password)

        val db = this.writableDatabase

        db.insert(TABLE_USERS, null, values)

        db.close()
    }

    fun addSports(sportsType: String, sportsName: String, instruction: String ){

        val values = ContentValues()

        values.put(SPORTSTYPE_COL, sportsType)
        values.put(SPORTSNAME_COL, sportsName)
        values.put(INSTRUCTION_COL, instruction)

        val db = this.writableDatabase

        db.insert(TABLE_SPORTS, null, values)

        db.close()
    }

    fun addNews(imageUrl: String, title: String, description: String ){

        val values = ContentValues()

        values.put(NEWS_IMAGE_URL_COL, imageUrl)
        values.put(NEWS_TITLE_COL, title)
        values.put(NEWS_DESCRIPTION_COL, description)

        val db = this.writableDatabase

        db.insert(TABLE_NEWS, null, values)

        db.close()
    }

    fun addAthletes(athleteName: String, sportName: String, country: String, bestPerformance: String, medals: String, facts: String){

        val values = ContentValues()

        values.put(ATHLETES_NAME_COL, athleteName)
        values.put(ATHLETES_SPORT_NAME_COL, sportName)
        values.put(ATHLETES_COUNTRY_COL, country)
        values.put(ATHLETES_BEST_PERFORMANCE_COL, bestPerformance)
        values.put(ATHLETES_MEDALS_COL, medals)
        values.put(ATHLETES_FACTS_COL, facts)

        val db = this.writableDatabase

        db.insert(TABLE_ATHLETES, null, values)

        db.close()
    }

    fun addEvents(eventsName: String, date: String, description: String ){

        val values = ContentValues()

        values.put(EVENTS_NAME_COL, eventsName)
        values.put(EVENTS_DATE_COL, date)
        values.put(EVENTS_DESCRIPTION_COL, description)

        val db = this.writableDatabase

        db.insert(TABLE_EVENTS, null, values)

        db.close()
    }

    fun addArchive(archiveName: String, date: String, description: String ){

        val values = ContentValues()

        values.put(ARCHIVE_NAME_COL, archiveName)
        values.put(ARCHIVE_DATE_COL, date)
        values.put(ARCHIVE_DESCRIPTION_COL, description)

        val db = this.writableDatabase

        db.insert(TABLE_ARCHIVE, null, values)

        db.close()
    }

    fun doesUserNameExist(username: String): Boolean {
        val columns = arrayOf(USERNAME_COL)

        val db = this.readableDatabase

        val selection = "$USERNAME_COL = ?"

        val selectionArgs = arrayOf(username)

        val cursor: Cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)

        val cursorCount: Int = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    fun doesUserExist(username: String, password: String): Boolean {
        val columns = arrayOf(
            USERNAME_COL
        )

        val db = this.readableDatabase

        val selection = "$USERNAME_COL = ? AND $PASSWORD_COL = ?"

        val selectionArgs = arrayOf(username, password)

        val cursor: Cursor = db.query(
            TABLE_USERS,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val cursorCount: Int = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    fun getSports(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_SPORTS, null)
    }

    fun getNews(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_NEWS, null)
    }

    fun getAthletes(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_ATHLETES, null)
    }

    fun getEvents(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null)
    }

    fun getArchives(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_ARCHIVE, null)
    }

    companion object{

        private val DATABASE_NAME = "SportsNewsAndInformationApp"

        private val DATABASE_VERSION = 1

        val TABLE_SPORTS = "sports_table"

        val ID_COL = "id"

        val SPORTSTYPE_COL = "sportsType"

        val SPORTSNAME_COL = "sportsName"

        val INSTRUCTION_COL = "instruction"


        val TABLE_NEWS = "news_table"

        val NEWS_IMAGE_URL_COL = "imageUrl"

        val NEWS_TITLE_COL = "title"

        val NEWS_DESCRIPTION_COL = "description"

        val TABLE_ATHLETES = "athletes_table"

        val ATHLETES_NAME_COL = "athleteName"

        val ATHLETES_SPORT_NAME_COL = "sportName"

        val ATHLETES_COUNTRY_COL = "country"

        val ATHLETES_BEST_PERFORMANCE_COL = "bestPerformance"

        val ATHLETES_MEDALS_COL = "medals"

        val ATHLETES_FACTS_COL = "facts"


        val TABLE_EVENTS = "events_table"

        val EVENTS_NAME_COL = "eventsName"

        val EVENTS_DATE_COL = "date"

        val EVENTS_DESCRIPTION_COL = "description"

        val TABLE_ARCHIVE = "archive_table"

        val ARCHIVE_NAME_COL = "archiveName"

        val ARCHIVE_DATE_COL = "date"

        val ARCHIVE_DESCRIPTION_COL = "description"


        val TABLE_USERS= "users_table"

        val USERNAME_COL = "username"

        val PASSWORD_COL = "password"
    }
}