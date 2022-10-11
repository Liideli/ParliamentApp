package com.example.parliamentapp.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * Defines a database and specifies data tables that will be used.
 * Version is incremented as new tables/columns are added/removed/changed.
 */

@Database(entities = [ParliamentMember::class], version = 1, exportSchema = false)
abstract class MemberDatabase: RoomDatabase() {

    abstract val parliamentMemberDao: ParliamentMemberDao

    companion object {
        @Volatile
        private var INSTANCE: MemberDatabase? = null

        fun getDatabase(context: Context): MemberDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MemberDatabase::class.java,
                    "member_table")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}