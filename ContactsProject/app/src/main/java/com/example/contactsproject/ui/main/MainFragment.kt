package com.example.contactsproject.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import com.example.contactsproject.R

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsproject.Product
import androidx.fragment.app.viewModels

import java.util.*

import com.example.contactsproject.databinding.MainFragmentBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(){

    private var adapter: ProductListAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel: MainViewModel by viewModels()
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
            val quantity = binding.phoneNumber.text.toString()

            if (name != "" && quantity != "") {
                val product = Product(name, quantity)
                viewModel.insertProduct(product)
                clearFields()
            } else {
                Snackbar.make(requireView(), "You must enter a name and a phone number", LENGTH_SHORT).show()
            }
        }

        binding.findButton.setOnClickListener { viewModel.findProduct(binding.contactName.text.toString()) }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteProduct(binding.contactName.text.toString())
            clearFields()
        }
    }

    private fun observerSetup() {

        viewModel.getAllProducts()?.observe(viewLifecycleOwner, Observer { products ->
            products?.let  {
                adapter?.setProductList(it)
            }
        })

        viewModel.getSearchResults().observe(viewLifecycleOwner, Observer { products ->

            products?.let {
                if (it.isNotEmpty()) {
//                    binding.productID.text = it[0].id.toString()
//                    binding.contactName.setText(it[0].contactName)
//                    binding.phoneNumber.setText(it[0].phoneNumber);
                    adapter?.setProductList(it)
                } else {
                   Snackbar.make(requireView(), "No names to Display", LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun recyclerSetup() {
        adapter = ProductListAdapter(R.layout.product_list_item)
        binding.productRecycler.layoutManager = LinearLayoutManager(context)
        binding.productRecycler.adapter = adapter
    }

    private fun clearFields() {
        binding.contactName.setText("")
        binding.phoneNumber.setText("")
    }
}