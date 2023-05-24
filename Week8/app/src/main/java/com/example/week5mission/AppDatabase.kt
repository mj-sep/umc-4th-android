package com.example.week5mission

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Memo::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun memoDao(): MemoDAO

    companion object {
        private var appDatabase: AppDatabase? = null

        /*
        private val MIGRATION_1_TO_2: Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.run {
                    execSQL("ALTER TABLE User ADD likes BOOLEAN NOT NULL")
                }
            }
        }

         */

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if(appDatabase == null) {
                synchronized(AppDatabase::class.java) {
                    appDatabase = Room.databaseBuilder( // 데이터베이스 객체 생성
                        context.applicationContext, AppDatabase::class.java, "memo-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return appDatabase
        }
    }
}