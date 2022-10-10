package com.example.parliamentapp.membersDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.parliamentapp.R
import com.example.parliamentapp.databinding.FragmentMemberDetailBinding
import com.example.parliamentapp.network.MemberDatabase

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * MemberDetailFragment inflates the layout, creates memberDetailViewModel
 */

class MemberDetailFragment : Fragment() {

    private lateinit var binding: FragmentMemberDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflates the layout
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_member_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments =
            MemberDetailFragmentArgs.fromBundle(requireArguments())

        // Set the data source
        val dataSource = MemberDatabase.getDatabase(application).parliamentMemberDao

        val viewModelFactory = MemberDetailViewModelFactory(dataSource, arguments.hetekaId)

        val memberDetailViewModel =
            ViewModelProvider(this, viewModelFactory)[MemberDetailViewModel::class.java]


        binding.memberDetailViewModel = memberDetailViewModel

        // Set this view as LiveData owner
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}