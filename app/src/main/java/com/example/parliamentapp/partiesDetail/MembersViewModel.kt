package com.example.parliamentapp.partiesDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentapp.network.ParliamentMemberDao
import com.example.parliamentapp.repository.MembersRepository

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * This file includes The MembersViewModel and its ViewModelFactory
 */

class MembersViewModel(membersRepository: MembersRepository, party: String) : ViewModel() {

    // Create navigation
    private val _navigateToParliamentMemberDetails = MutableLiveData<Int>()
    val navigateToParliamentMemberDetails: LiveData<Int>
        get() = _navigateToParliamentMemberDetails

    // Gets list of members of defined party
    val parliamentMembers = membersRepository.getParliamentMembersByParty(party)

    // Set _navigateToParliamentMemberDetails to the selected item
    fun onParliamentMemberClicked(id: Int) {
        _navigateToParliamentMemberDetails.value = id
    }

    // Null the value of _navigateToParliamentMemberDetails, avoiding the navigation
    // being happened again when the user is returned from the memberDetailFragment
    fun onParliamentMemberDetailsComplete() {
        _navigateToParliamentMemberDetails.value = null
    }
}

// Creates new MembersViewModel for MembersFragment
class MembersViewModelFactory(
    private val parliamentMemberDao: ParliamentMemberDao,
    private val party: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MembersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MembersViewModel(MembersRepository(parliamentMemberDao), party) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}