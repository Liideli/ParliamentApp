package com.example.parliamentapp.partiesDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.parliamentapp.R
import com.example.parliamentapp.databinding.FragmentMembersBinding
import com.example.parliamentapp.network.MemberDatabase

/**
 * Author: Roope Laine
 * Student ID: 2114735
 * Date: 11.10.2022
 *
 * MembersFragment inflates the layout, creates membersViewModel and sets membersAdapter
 */

class MembersFragment : Fragment() {

    private lateinit var binding: FragmentMembersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for Members Fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_members, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments =
            MembersFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = MembersViewModelFactory(
            MemberDatabase.getDatabase(application).parliamentMemberDao,
            arguments.party
        )

        // Creates the membersViewModel for the fragment
        val membersViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MembersViewModel::class.java)

        // Set the LifecycleOwner to this for observing changes of LiveData
        binding.lifecycleOwner = this

        val adapter = MembersAdapter(ParliamentMemberListener { hetekaId ->
            membersViewModel.onParliamentMemberClicked(hetekaId)
        })

        // MembersAdapter is set to RecyclerView
        binding.membersList.adapter = adapter

        // Observes membersViewModels members and submits new list if it changes
        membersViewModel.parliamentMembers.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
        // On member click the fragment is changed to defined members details
        membersViewModel.navigateToParliamentMemberDetails.observe(viewLifecycleOwner) { member ->
            member?.let {
                this.findNavController().navigate(
                    MembersFragmentDirections.actionMembersFragmentToMemberDetailFragment(
                        member
                    )
                )
                membersViewModel.onParliamentMemberDetailsComplete()
            }
        }

        return binding.root
    }

}