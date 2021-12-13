package com.example.contactsproject.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsproject.Contact
import androidx.fragment.app.viewModels

import com.example.contactsproject.databinding.MainFragmentBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnButtonClickListener{

    private var adapter: ContactListAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }


    private val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listenerSetup()
        observerSetup()
        recyclerSetup()

    }

    private fun listenerSetup() {

        binding.addButton.setOnClickListener {
            val name = binding.contactName.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()

            if (name != "" && phoneNumber != "") {
                val contact = Contact(name, phoneNumber)
                viewModel.insertProduct(contact)
                clearFields()
                observerSetup()
            } else {
                Snackbar.make(requireView(), "You must enter a name and a phone number", LENGTH_SHORT).show()
            }
        }

        binding.findButton.setOnClickListener {
            if(binding.contactName.text.toString() != "") {
                viewModel.findProduct(binding.contactName.text.toString())
            } else {
                Snackbar.make(requireView(), "You must enter a search criteria in the name field", LENGTH_SHORT).show()
            }
        }
        binding.ASCButton.setOnClickListener {
            viewModel.sortASC()
            observerSetup()
        }
        binding.DESCButton.setOnClickListener {
            viewModel.sortDESC()
            observerSetup()
        }
    }

    private fun observerSetup() {

        viewModel.getAllContacts()?.observe(viewLifecycleOwner, { contacts ->
            contacts?.let  {
                adapter?.setContactList(it)
            }
        })

        viewModel.getSearchResults().observe(viewLifecycleOwner, { contacts ->

            contacts?.let {
                if (it.isNotEmpty()) {
                    adapter?.setContactList(it)
                } else {
                   Snackbar.make(requireView(), "No names found matching the criteria", LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun recyclerSetup() {
        adapter = ContactListAdapter(this)
        binding.productRecycler.layoutManager = LinearLayoutManager(context)
        binding.productRecycler.adapter = adapter
    }

    private fun clearFields() {
        binding.contactName.setText("")
        binding.phoneNumber.setText("")
    }

    override fun onButtonClick(data: Contact) {
        viewModel.deleteProduct(data.id)
    }
}