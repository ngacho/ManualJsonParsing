package com.example.manualjsonparsing.view.addcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manualjsonparsing.util.JsonParser
import com.example.manualjsonparsing.util.URLConstants
import kotlinx.coroutines.launch

class AddContactViewModel : ViewModel() {
    private val contactJsonObject = JsonParser.createJSonObject("Saba Relf", "srelf0@java.com", "964 931 6848")

    fun addContact()= viewModelScope.launch{
            JsonParser.sendPOSTRequest(URLConstants.ADD_CONTACT_URL, contactJsonObject)
        }

}