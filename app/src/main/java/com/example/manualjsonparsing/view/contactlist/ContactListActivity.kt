package com.example.manualjsonparsing.view.contactlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.manualjsonparsing.R
import com.example.manualjsonparsing.databinding.ActivityMainBinding
import com.example.manualjsonparsing.util.ContactListAdapter
import com.example.manualjsonparsing.util.Status
import com.example.manualjsonparsing.util.URLConstants
import com.example.manualjsonparsing.view.addcontact.AddContactActivity

class ContactListActivity : AppCompatActivity() {

    private lateinit var mainViewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactListAdapter = ContactListAdapter()
        mainViewModel =
            ContactListViewModel()

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main
            )

        val recyclerView = binding.contactsRecycler
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = contactListAdapter

        val progressBar = binding.progressMonitor


        mainViewModel.contactResource.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    contactListAdapter.submitList(it.data)
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Oops, It is not you, it is us. Please give it another try",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_add -> {
            val newContactIntent = Intent(applicationContext, AddContactActivity::class.java)
            startActivity(newContactIntent)
            true
        }
        else -> false
    }

}