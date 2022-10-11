package com.example.parliamentapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * The setup of ParliamentApiService for Api data fetching
 * Converts JSON data from the URL into a list
 */
private const val BASE_URL = "https://users.metropolia.fi/~peterh/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Using the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ParliamentApiService {
    @GET("seating.json")
    suspend fun getParliamentMembers(): List<ParliamentMember>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ParliamentApi {
    val members: ParliamentApiService by lazy { retrofit.create(ParliamentApiService::class.java) }
}

enum class ParliamentApiStatus { LOADING, DONE }