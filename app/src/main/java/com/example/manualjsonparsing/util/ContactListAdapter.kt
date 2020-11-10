package com.example.manualjsonparsing.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.manualjsonparsing.R
import com.example.manualjsonparsing.databinding.ContactItemBinding
import com.example.manualjsonparsing.pojo.Contact

class ContactListAdapter(
    diffUtil: DiffUtil.ItemCallback<Contact> = CONTACT_COMPARATOR
) :
    ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        buildViewHolder(parent)

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(getItem(position))


    class ContactViewHolder(private val contactItemBinding: ContactItemBinding) :
        RecyclerView.ViewHolder(contactItemBinding.root) {

        fun bind(contact: Contact) {
            contactItemBinding.contact = contact
            contactItemBinding.executePendingBindings()
        }
    }

    companion object {

        val CONTACT_COMPARATOR = object :
            DiffUtil.ItemCallback<Contact>() {

            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.id == newItem.id && oldItem == newItem

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem == newItem
        }

        fun buildViewHolder(parent: ViewGroup): ContactViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)

            val contactItemBinding = DataBindingUtil.inflate<ContactItemBinding>(
                layoutInflater,
                R.layout.contact_item,
                parent,
                false
            )

            return ContactViewHolder(contactItemBinding)
        }
    }
}