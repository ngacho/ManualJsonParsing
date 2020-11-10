package com.example.manualjsonparsing.view.addcontact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.manualjsonparsing.R
import com.example.manualjsonparsing.databinding.ActivityAddContactBinding
import com.example.manualjsonparsing.databinding.ActivityMainBinding

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addContactViewModel = AddContactViewModel()


        val addContactBinding =
            DataBindingUtil.setContentView<ActivityAddContactBinding>(
                this,
                R.layout.activity_add_contact
            )

        val saveButton = addContactBinding.saveButton
        saveButton.setOnClickListener {

            addContactViewModel.addContact()
        }


    }
}