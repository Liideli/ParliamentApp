package com.example.parliamentapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parliamentapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * MembersRepository for communication between app and the database
 */

class MembersRepository(private val parliamentMemberDao: ParliamentMemberDao) {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ParliamentApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ParliamentApiStatus>
        get() = _status

    init {
        _status.postValue(ParliamentApiStatus.LOADING)
    }

    // Gets List of ParliamentMembers objects from API and inserts into the database
    suspend fun refreshParliamentMembers() {
        withContext(Dispatchers.IO) {
            val members = ParliamentApi.members.getParliamentMembers()
            _status.postValue(ParliamentApiStatus.DONE)
            parliamentMemberDao.insertAll(members)
        }
    }

    // Get ParliamentMemberData all parties from the database
    fun getParties(): LiveData<List<Party>> {
        return parliamentMemberDao.getParties()
    }

    // Get ParliamentMemberData by party from the database
    fun getParliamentMembersByParty(party: String): LiveData<List<ParliamentMember>> {
        return parliamentMemberDao.getMembersByParty(party)
    }

    // Get ParliamentMemberData by hetekaId from the database
    fun getParliamentMembersById(id: Int): LiveData<ParliamentMember> {
        return parliamentMemberDao.getMemberById(id)
    }

}