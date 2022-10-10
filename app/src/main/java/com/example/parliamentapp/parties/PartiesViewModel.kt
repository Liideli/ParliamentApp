package com.example.parliamentapp.parties

import androidx.lifecycle.*
import com.example.parliamentapp.network.*
import com.example.parliamentapp.repository.MembersRepository

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * This file includes The PartiesViewModel and its ViewModelFactory
 */

class PartiesViewModel(membersRepository: MembersRepository) : ViewModel() {

    // Gets list of parties from MembersRepository
    val parties = membersRepository.getParties()

    // The external immutable LiveData for the request status from MembersRepository
    val status = membersRepository.status

    // Create navigation
    private val _navigateToSelectedParty = MutableLiveData<String>()
    val navigateToSelectedParty: LiveData<String>
        get() = _navigateToSelectedParty

    // Set _navigateToSelectedParty to the selected item
    fun onPartyClicked(party: String) {
        _navigateToSelectedParty.value = party
    }

    // Null the value of _navigateToSelectedParty, avoiding the navigation
    // being happened again when the user is returned from the membersFragment
    fun onNavigateToPartyComplete() {
        _navigateToSelectedParty.value = null
    }
}

// Creates new PartiesViewModel for PartiesFragment
class PartiesViewModelFactory(
    private val parliamentMemberDao: ParliamentMemberDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartiesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PartiesViewModel(MembersRepository(parliamentMemberDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}