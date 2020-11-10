package com.example.manualjsonparsing.view.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.manualjsonparsing.pojo.Contact
import com.example.manualjsonparsing.util.JsonParser
import com.example.manualjsonparsing.util.Resource
import com.example.manualjsonparsing.util.URLConstants


class ContactListViewModel : ViewModel() {

    val contactResource: LiveData<Resource<List<Contact>>>
        get() = getContactList()

    private fun getContactList() = JsonParser.parseJSon(URLConstants.GET_CONTACTS_URL)


}