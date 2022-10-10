package com.example.parliamentapp.parties


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentapp.bindLogo
import com.example.parliamentapp.databinding.ItemViewBinding
import com.example.parliamentapp.network.Party

/**
 * Author: Roope Laine
 * Student ID: 2114735
 *
 * Adapter for the RecyclerView list of parties
 */

class PartiesAdapter(private val clickListener: PartyListener) :
    ListAdapter<Party, PartiesAdapter.ViewHolder>(PartyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // RecyclerView calls this to display data to its defined positions
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
        holder.binding.imageView.bindLogo(getItem(position))
    }

    class ViewHolder private constructor(val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Bindings for XML items
        fun bind(item: Party, clickListener: PartyListener) {
            binding.party = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

// ListAdapter compares the new list to the old list and compares items that were previously added, removed or changed.
class PartyDiffCallback : DiffUtil.ItemCallback<Party>() {

    override fun areItemsTheSame(oldItem: Party, newItem: Party): Boolean {
        return oldItem.party == newItem.party
    }

    override fun areContentsTheSame(oldItem: Party, newItem: Party): Boolean {
        return oldItem == newItem
    }
}

// On click listener to membersFragment, party name as a parameter
class PartyListener(val clickListener: (party: String) -> Unit) {
    fun onClick(party: Party) = clickListener(party.party)
}
