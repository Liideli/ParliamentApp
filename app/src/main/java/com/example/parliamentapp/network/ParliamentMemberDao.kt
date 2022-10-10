package com.example.parliamentapp.network

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * Data access object for the ParliamentMember and Party data transfer objects
 */

@Dao
interface ParliamentMemberDao {

    @Query("SELECT party FROM Parliament GROUP BY party ORDER BY party ASC")
    fun getParties(): LiveData<List<Party>>

    @Query("SELECT * FROM Parliament WHERE party = :party ORDER BY fullname ASC")
    fun getMembersByParty(party: String): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM Parliament WHERE hetekaId = :hetekaId")
    fun getMemberById(hetekaId: Int): LiveData<ParliamentMember>

    // Checks the database for any conflict and replaces the data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ParliamentMember>)
}