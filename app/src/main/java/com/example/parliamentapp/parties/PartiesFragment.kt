package com.example.parliamentapp.parties

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parliamentapp.databinding.FragmentPartiesBinding
import com.example.parliamentapp.network.MemberDatabase

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * PartiesFragment inflates the layout, creates partiesViewModel and sets partiesAdapter
 */

class PartiesFragment : Fragment() {

    private lateinit var binding: FragmentPartiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for Parties Fragment
        binding = FragmentPartiesBinding.inflate(inflater, container, false)


        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            PartiesViewModelFactory(MemberDatabase.getDatabase(application).parliamentMemberDao)

        // Creates the partiesViewModel for the fragment
        val partiesViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(PartiesViewModel::class.java)

        // Set the LifecycleOwner to this for observing changes of LiveData
        binding.lifecycleOwner = this

        val adapter = PartiesAdapter(PartyListener { party ->
            partiesViewModel.onPartyClicked(party)
        })

        // PartiesAdapter is set to RecyclerView
        binding.partyList.adapter = adapter

        // Observes partiesViewModels parties and submits new list if it changes
        partiesViewModel.parties.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        // On party click the fragment is changed to defined partys members
        partiesViewModel.navigateToSelectedParty.observe(viewLifecycleOwner) { party ->
            party?.let {
                this.findNavController().navigate(
                    PartiesFragmentDirections.actionPartiesFragmentToMembersFragment(
                        party
                    )
                )
                partiesViewModel.onNavigateToPartyComplete()
            }
        }

        // Layout for Recyclerview set to LinearLayout
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.partyList.layoutManager = manager

        // Called to optimize RecyclerView
        binding.partyList.setHasFixedSize(true)

        return binding.root
    }

}