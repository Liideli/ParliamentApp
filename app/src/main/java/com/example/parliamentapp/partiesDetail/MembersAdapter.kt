package com.example.parliamentapp.partiesDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentapp.databinding.ItemViewMembersBinding
import com.example.parliamentapp.network.ParliamentMember

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * Adapter for the RecyclerView list of members of a defined party
 */

class MembersAdapter(private val clickListener: ParliamentMemberListener) :
    ListAdapter<ParliamentMember, MembersAdapter.ViewHolder>(ParliamentMemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // RecyclerView calls this to display data to its defined positions
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ItemViewMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Bindings for XML items
        fun bind(item: ParliamentMember, clickListener: ParliamentMemberListener) {
            binding.member = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewMembersBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

// ListAdapter compares the new list to the old list and compares items that were previously added, removed or changed.
class ParliamentMemberDiffCallback : DiffUtil.ItemCallback<ParliamentMember>() {

    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }

    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem == newItem
    }
}

// On click listener to memberDetailFragment, hetekaId as a parameter
class ParliamentMemberListener(val clickListener: (hetekaId: Int) -> Unit) {
    fun onClick(member: ParliamentMember) = clickListener(member.hetekaId)
}
