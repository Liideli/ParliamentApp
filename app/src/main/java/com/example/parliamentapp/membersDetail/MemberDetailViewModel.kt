package com.example.parliamentapp.membersDetail


import androidx.lifecycle.*
import com.example.parliamentapp.network.ParliamentMember
import com.example.parliamentapp.network.ParliamentMemberDao
import com.example.parliamentapp.repository.MembersRepository

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * This file includes The MemberDetailViewModel and its ViewModelFactory
 */

class MemberDetailViewModel(membersRepository: MembersRepository, hetekaId: Int) : ViewModel() {

    private var _member = MutableLiveData<LiveData<ParliamentMember>>()
    val member: LiveData<LiveData<ParliamentMember>>
        get() = _member


    init {
        _member.value = membersRepository.getParliamentMembersById(hetekaId)
    }
}

// Creates new MemberDetailViewModel for MemberDetailFragment
class MemberDetailViewModelFactory(
    private val parliamentMemberDao: ParliamentMemberDao,
    private val hetekaId: Int,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberDetailViewModel(MembersRepository(parliamentMemberDao), hetekaId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}