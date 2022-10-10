package com.example.parliamentapp.network

import androidx.room.*

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * ParliamentMember data transfer objects in this file. These are responsible for parsing responses from the server.
 */

// Parliament Member Data Class
@Entity(tableName = "Parliament")
data class ParliamentMember(
    @PrimaryKey(autoGenerate = false)
    val hetekaId: Int,
    val firstname: String,
    val lastname: String,
    val minister: Boolean,
    val party: String,
    val pictureUrl: String,
    val seatNumber: Int,
    var fullname: String = "$firstname $lastname",
)
