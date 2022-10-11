package com.example.parliamentapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.load.HttpException
import com.example.parliamentapp.network.MemberDatabase.Companion.getDatabase
import com.example.parliamentapp.repository.MembersRepository

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * Worker for updating the database with the remote data
 */

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val RefreshDatabase = "com.example.parliamentapp.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = MembersRepository(database.parliamentMemberDao)

        try {
            repository.refreshParliamentMembers()
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }

}